import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

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
