package xoclient;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static xoclient.XOClient.client;
import static xoclient.XOClient.setMoveListener;


/**
 * FXML Controller class
 *
 * @author Aalaa Habib
 */
public class LoginController implements Initializable {

    String playerName = "";
    String playerPassword = "";
    Validation valid = new Validation();
    @FXML
    public TextField userName;
    public AnchorPane ap2;
    public AnchorPane apsign;

    public TextField password;
    public Button loginBtn;
    public Button registerBtn;
    public Label errorLb;
    public Label passError;
    boolean login = false;

    Stage primaryStage;

    public LoginController(Stage _primaryStage) {
        this.primaryStage = _primaryStage;
    }

    public void clear() {
        errorLb.setText("");
        passError.setText("");
    }

    public boolean validation() {
        boolean check_name, check_pass;
        clear();
        check_name = valid.isValid_userName(userName.getText());
        check_pass = valid.isValid_password(password.getText());
        if (!check_name || !check_pass) {
            if (!check_name) {
                errorLb.setText("Name must be charachters only [3,20] digit");
            }
            if (!check_pass) {
                passError.setText("Password must be 4 digit at least up To 20");
            }
            return false;
        }
        return true;
    }

    public String sendData_login() {
        String Data;
        playerName = userName.getText();
        playerPassword = password.getText();
        Data = "#login" + "," + playerName + "," + playerPassword;
        return Data;
    }

    public String sendData_register() {
        String Data;
        playerName = userName.getText();
        playerPassword = password.getText();
        Data = "#register" + "," + playerName + "," + playerPassword;
        return Data;
    }

    double xOffset = 0;
    double yOffset = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        setMoveListener(ap2,primaryStage);
        setMoveListener(apsign,primaryStage);
    }



    @FXML
    private void min(MouseEvent event) {
        // System.err.println("min");
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        //Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        //s.setFullScreen(true);
    }

    @FXML
    private void close(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @FXML
    private void login(ActionEvent event) {
        if (validation()) {
            if (client == null) {
                client = new ClientThread("127.0.0.1", 4433, this);
            }
            client.sendMsg(sendData_login());
            login = true;
        }
    }

    @FXML
    private void register() {
        if (validation()) {
            if (client == null) {
                client = new ClientThread("127.0.0.1", 4433, this);
            }
            client.sendMsg(sendData_register());
            login = false;
        }
    }

    public void authorizeResult(String msg) {
        switch (msg) {
            case "#done":
                successLogin();
                break;
            case "#no":
                if (login) {
                    failedLogin();
                } else {
                    failedRegister();
                }
                break;
            case "#already":
                alreadyLoggedIn();
                break;
            default:
                break;
        }
    }

    public void successLogin() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Athentication");
        alert.setHeaderText("Login Info");
        alert.setContentText("Logged Successfully ");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
                    // Create a controller instance
                    DashboardController dashboardController = new DashboardController(primaryStage, playerName);
                    // Set it in the FXMLLoader
                    loader.setController(dashboardController);
                    primaryStage.setTitle("XO Dashboard");
                    Scene scene = new Scene((Parent) loader.load());
                    primaryStage.setScene(scene);
//                    primaryStage.show();

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void failedLogin() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Athentication");
        alert.setHeaderText("Login Failed");
        alert.setContentText("Wrong Username or Password !");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void alreadyLoggedIn() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Login");
        alert.setHeaderText("Login Failed");
        alert.setContentText("Username Already Logged in !");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void failedRegister() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Registration");
        alert.setHeaderText("Registration Failed");
        alert.setContentText("Username Exists, Try Another One !");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void playWithAi() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SinglePlayer.fxml"));
        // Create a controller instance
        SinglePlayerController singlePlayerController = new SinglePlayerController(primaryStage);
        // Set it in the FXMLLoader
        loader.setController(singlePlayerController);
        primaryStage.setTitle("XO Dashboard");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
    }

    public void playWithFriend() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiPlayer.fxml"));
        // Create a controller instance
        MultiPlayerController multiPlayerController = new MultiPlayerController(primaryStage);
        // Set it in the FXMLLoader
        loader.setController(multiPlayerController);
        primaryStage.setTitle("XO Dashboard");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
    }
}
