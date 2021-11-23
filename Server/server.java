import java.net.*;
import java.io.*;
public class server{
 public static void main(String[] args) {
   //initialization
    Socket client =null;
    ServerSocket ss = null;
    DataInputStream inputClient =null;
    DataInputStream inputServer = null;
    DataOutputStream outputServer =null;
   //running Server
   try{
    ss = new ServerSocket(3333);
    System.out.println("Server running :"+ss);
    client =ss.accept();
    System.out.println("New client connected : "+client);
    inputServer = new DataInputStream(client.getInputStream());
    inputClient = new DataInputStream(client.getInputStream());
    outputServer = new DataOutputStream(client.getOutputStream());
    System.out.println("New thread asseigned to this client");
    MultiClientHandler t = new MultiClientHandler(client,inputClient,inputServer,outputServer);
    t.start();
    if(!t.isUP()){
      client.close();
      ss.close();
      System.out.println("Server Closed");
       };
      }catch(Exception e){
        System.out.println("Error : "+e);
      }
   }
 }
