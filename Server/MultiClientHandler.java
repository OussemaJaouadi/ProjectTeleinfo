import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MultiClientHandler extends Thread {
  //initialization
  private DataInputStream inputClient = null;
  private DataInputStream inputServer = null;
  private Scanner sc;
  private DataOutputStream outputServer = null;
  private Socket clientSocket = null;
  private boolean up=true,exitClient=false ;
  //creation du constructeur
  public MultiClientHandler(Socket clientSocket,DataInputStream inputClient,DataInputStream inputServer,DataOutputStream outputServer){
    this.clientSocket=clientSocket;
    this.inputClient=inputClient;
    this.inputServer=inputServer;
    this.outputServer=outputServer;
    this.up=true;
  }
  public void run(){
    String line="";
    String recieved="";
    //method that will be executed when t.start() invoked
    while(true){
      System.out.println("server send :");
      serverSendsData(line);
      if(!up){
        break;
      };
      System.out.println("server recieved :");
      serverRecieveData(recieved);
      if(exitClient){
        break;
      }
    }
    //closing the session
    try{
      this.inputServer.close();
      this.outputServer.close();
      this.inputClient.close();
    }catch(IOException i){
      System.out.println("Error : "+i);
    }
  }
  public void serverSendsData(String line){
    try{
      line=inputServer.readLine();
      if(line.equals("Exit")){
        System.out.println("Server shutting down....\nClosing clients");
        stateDown();
        this.clientSocket.close();
      };
      outputServer.writeUTF(line);
    }catch(IOException i){
      System.out.println("Error : "+i);
    }
  };
  public void serverRecieveData(String recieved){
    try{
      recieved=inputClient.readUTF();
      if(recieved.equals("Exit")){
      System.out.println(this.clientSocket+" sends exit...");
      this.clientSocket.close();
      System.out.println("Connection closed");
      exitClient=true;
    };
    System.out.println(this.clientSocket+"Responded :"+recieved);
  }catch(IOException i){
    System.out.println("Error : "+i);
  }
  };
  public void stateDown(){this.up=false;};
  public boolean isUP(){return this.up;};
}
