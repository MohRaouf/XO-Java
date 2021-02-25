package xoclient;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

public class ClientThread extends Thread {

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    LoginController loginController;
    public DashboardController dashboardController;

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
                System.out.println("Received Message : " + msg);
                if (msg != null) {
                    if (msg.contains("#")) {
                        loginResult(msg);
                    } else if (msg.startsWith("@")) {
                        populateInfo(msg);
                    } else if (msg.startsWith("$yes")) {
                        gameInfo(msg);
                    } else if (msg.startsWith("$")) {
                        offerToPlay(msg);
                    }
                }
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

    private void populateInfo(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    dashboardController.showInformation(msg);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void offerToPlay(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dashboardController.ask_to_play(msg);
            }
        });
    }

    private void gameInfo(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dashboardController.play_game(msg);
            }
        });
    }

}
