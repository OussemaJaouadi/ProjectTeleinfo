import java.net.*;
import java.io.*;
import java.util.Scanner;
public class client{
  //initialization du Socket
  private Socket clientSocket = null ;
  private DataInputStream inputClient = null ;
  private DataOutputStream outputClient = null ;
  private DataInputStream inputServer = null;
  private boolean exit =false;
  //constructeur
  public client(String adresse,int port){
    try{
      //socket creation
      clientSocket = new Socket(adresse,port);
      System.out.println("Connected to server");
      //Send data Data
      inputClient = new DataInputStream(System.in);
      inputServer = new DataInputStream(clientSocket.getInputStream());
      outputClient = new DataOutputStream(clientSocket.getOutputStream());
      exit = false;
      //recieve data
      String line ="";
      String recieved="";
      while (true)
      {
        System.out.println("client Sends :");
        clientRecieveData(recieved);
        System.out.println("client recieved :");
        clientSendData(line);
        if(exit){
          break;
        }

      }
      closingSession();
    }catch(UnknownHostException u){
      System.out.println("Error : "+u);
    }catch(IOException i){
      System.out.println("Error : "+i);
    }
  }
  public void clientSendData(String line){
    try{
      line = inputClient.readLine();
      if(line.equals("Exit")){
        System.out.println("Client exiting...");
        this.clientSocket.close();
        System.out.println("Connection closed");
        wantExit();
      }
      outputClient.writeUTF(line);
    }catch(IOException i){
      System.out.println("Error : "+i);
    }
  };
  public void wantExit(){this.exit=true;};
  public void clientRecieveData(String recieved){
    try{
      recieved=inputServer.readUTF();
      System.out.println("The server sends : "+recieved);
    }catch(IOException i){
      System.out.println(i);}
  };
  public void closingSession(){
    try{
      inputServer.close();
      inputClient.close();
      outputClient.close();
      clientSocket.close();
    }catch(IOException i){
      System.out.println(i);
    }
  };
  public static void main(String[] args) {
    client c = new client("localhost",3333);
  }
}
