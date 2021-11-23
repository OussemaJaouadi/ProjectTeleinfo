import java.io.*;
import java.net.*;
import java.util.Scanner;
public class MultiClient{
  public static void main(String[] args) throws IOException {
    try{
      Scanner sc = new Scanner(System.in);
      Socket client = new Socket("localhost",3333);
    }catch (IOException e) {
      System.out.println(e);
    }
  }
}
