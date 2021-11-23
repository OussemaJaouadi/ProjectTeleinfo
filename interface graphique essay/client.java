import java.net.*;
import java.io.*;

//Création de la classe Client qui lui permet de demander à un serveur les données d'un étudiant par son num carte étudiant.

public class client {
  private Socket socket = null;
  private ObjectInputStream in = null;
  private DataOutputStream out = null;

  public client(String adress, int port) {
    try {
      socket = new Socket(adress, port);
      out = new DataOutputStream(socket.getOutputStream());
      in = new ObjectInputStream(socket.getInputStream());
    } catch (UnknownHostException u) {
      System.out.println("1 / " + u);
    } catch (IOException i) {
      System.out.println("2 / " + i);
    }

  }

  public void sendToServer(String str) {
    try {
      out.writeUTF(str);
    } catch (IOException i) {
      System.out.println(i);
    }
  }

  public String getResult() {
    String result="";
    try {
      result = in.readUTF();

    } catch (IOException e) {
      System.out.println("3 / " + e);
    } 
    return result;
  }

  public boolean isConnected() {
    if (socket != null)
      return socket.isConnected();
    else
      return false;

  }

  public void close() {
    try {
      out.close();
      socket.close();
    } catch (IOException i) {
      System.out.println("5 / " + i);
    }
  }

}
