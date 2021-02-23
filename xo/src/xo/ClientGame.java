package xo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientGame {
    private final ServerSocket server = null;
    Socket s;
    String serverMessage = "";
    PrintWriter toServer = null;
    BufferedReader fromServer;
    String mssg;
    
    
    
   /* Client Thread  Class*/
  public class Client extends Thread {

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    public Client(String ip, int port) {
      try {
        mySocket = new Socket(ip, port);
        dis = new DataInputStream(mySocket.getInputStream());
        ps = new PrintStream(mySocket.getOutputStream());
        start();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    
    }

    public void sendMsg(String msg) {
      try {
        ps.println(msg);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public void run() {
      while (true) {
        try {
          String receivedMsg = dis.readLine();
          System.out.println(receivedMsg);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }
}
