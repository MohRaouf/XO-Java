package xoclient;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import static xoclient.XOClient.client;

public class ClientThread extends Thread {

    Socket mySocket;
    DataInputStream dis;
    PrintStream ps;

    LoginController loginController;
    public DashboardController dashboardController;
    public GameGLobalController gameController;
    Stage primaryStage;
    boolean run = true;

    public ClientThread(String ip, int port, LoginController _loginController) {
        try {
            mySocket = new Socket(ip, port);
            dis = new DataInputStream(mySocket.getInputStream());
            ps = new PrintStream(mySocket.getOutputStream());
            loginController = _loginController;  //set LoginController Ref so we can control its view
            primaryStage = _loginController.primaryStage;
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
            lostConnection();
        }
    }

    public void end() {
        run = false;
    }

    @Override
    public void run() {
        try {
            while (run) {
                String msg = dis.readLine();
                System.out.println("Received Message : " + msg);
                if (msg != null) {
                    if (msg.contains("#")) {
                        loginResult(msg);
                    } else if (msg.startsWith("@")) {
                        populateInfo(msg);
                    } else if (msg.startsWith("$yes")) {
                        gameInfo(msg);
                    } else if (msg.startsWith("$no")) {
                        inviteRefused(msg);
                    } else if (msg.startsWith("$offline")) {
                        offlineOpponent(msg);
                    } else if (msg.startsWith("$ingame")) {
                        inGameOpponent(msg);
                    } else if (msg.startsWith("$")) {
                        offerToPlay(msg);
                    } else if (msg.startsWith(">")) {
                        gameMove(msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logoutAction();
        }
    }

    private void inviteRefused(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dashboardController.refusedToPlay(msg);
            }
        });
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
                dashboardController.invitedToPlay(msg);
            }
        });
    }

    private void offlineOpponent(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dashboardController.offlineOpponent(msg);
            }
        });
    }

    private void inGameOpponent(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dashboardController.inGameOpponent(msg);
            }
        });
    }

    private void gameInfo(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    dashboardController.play_game(msg);
                } catch (IOException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private void gameMove(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameController.DrawReceived(msg);
            }
        });
    }

    private void logoutAction() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                    // Create a controller instance
                    LoginController loginController = new LoginController(primaryStage);
                    // Set it in the FXMLLoader
                    loader.setController(loginController);
                    primaryStage.setTitle("XO Login");
                    Scene scene = new Scene((Parent) loader.load());
                    primaryStage.setScene(scene);
                    lostConnection();
                } catch (IOException ex) {
                    Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void lostConnection() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Lost");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
                buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family:Berlin Sans FB;"
                        + "-fx-background-color:#ffffff;"
                        + "-fx-text-fill: #FE4451;"
                        + "-fx-background-radius: 15;"));
                alert.setHeaderText("Connection Lost");
                alert.setContentText("Failed to Connect to the Server !");
                alert.showAndWait().ifPresent(rs -> {
                    client = null;
                    if (rs == ButtonType.OK) {
                        System.out.println("Pressed OK.");
                    }
                });
            }
        });

    }

}
