package xoclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import javafx.application.Platform;

public class ClientThread extends Thread {

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    LoginController loginController;

    public ClientThread(String ip, int port, LoginController _loginController) {
        try {
            mySocket = new Socket(ip, port);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            loginController = _loginController;  //set LoginController Ref so we can control its view
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
                String msg = dis.readLine();
                if (msg != null) {
                    if (msg.contains("#")) {
                        loginResult(msg);
                    }
                }
                System.out.println(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loginResult(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                loginController.authorizeResult(msg);
            }
        });
    }

}
