/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import static xoclient.XOClient.client;
import static xoclient.XOClient.setMoveListener;

/**
 * FXML Controller class
 *
 * @author esraa abou alkassem
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane ap2;
//    private TextField tf2;
    @FXML
    private ListView<HBox> topplayer;
    @FXML
    private ListView<HBox> avplayer;
    @FXML
    private Label close;
    @FXML
    private Button ask;
    @FXML
    private ImageView loader;
    @FXML
    private Pane loginuser;
    @FXML
    private Button Refresh;

    Stage primaryStage;

    String selected_Player;
    String username;
    @FXML
    private Button back;

    public DashboardController(Stage _primaryStage, String _username) {
        primaryStage = _primaryStage;
        client.dashboardController = this;
        username = _username;
        client.sendMsg("get info");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        login_user(username);
        setMoveListener(ap2,primaryStage);
    }

    public void login_user(String user) {
        Circle cir2 = new Circle(35, 35, 17);
        cir2.setStroke(Color.DARKSLATEBLUE);
        Image im = new Image("xoclient/user.png", false);
        cir2.setFill(new ImagePattern(im));
        cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.CORNSILK));
        Label lbName = new Label(user);///get name 
        lbName.setStyle("fx-background-radius: 20;"
                + "-fx-text-fill:#FE4451;"
                + "-fx-font-size: 12pt ;"
                + "-fx-background-insets:10 0 1 0;"
                + "-fx-padding:0 0 10 0;"
                + "-fx-font-weight: bold;"
                + "-fx-margin-left:30px;");
        lbName.setGraphic(cir2);
        loginuser.getChildren().addAll(lbName);
        loginuser.setStyle("visibility: visible;");
    }

    public void showInformation(String list) throws FileNotFoundException {
        System.out.println("List with @ : " + list);
        String listplayer = list.split("[@]")[1];
        System.out.println("Without @ : " + listplayer);

        String[] player = listplayer.split("[,]");
        String[][] data = new String[20][3];
        for (int i = 0; i < player.length; i++) {
            StringTokenizer st = new StringTokenizer(player[i], "|");
            int j = 0;
            while (st.hasMoreTokens()) {
                data[i][j] = st.nextToken();
                j++;
            }
        }
        for (int i = 0; i < player.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(data[i][j]);
            }
            System.out.println();
        }
        String[] arrOfStr = null;
        for (int i = 0; i < player.length; i++) {
            arrOfStr = (player[i]).split("[|]");
        }

        // Available list player
        avplayer.getItems().clear();
        topplayer.getItems().clear();
        for (int i = 0; i < player.length; i++) {
            Label lbl = new Label(data[i][0]);///get name 
            lbl.setId("availablePlayer");
            //ImageView img=new ImageView(getClass().getResource("user.png").toString());
            Circle cir2 = new Circle(30, 30, 15);
            Image im = new Image("xoclient/user.png", false);
            cir2.setFill(new ImagePattern(im));
            Circle c = null;
            if ((data[i][1]).equals("online"))//get status
            {
                cir2.setStroke(Color.SEAGREEN);
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.SEAGREEN));
                c = new Circle(5, javafx.scene.paint.Color.LIGHTGREEN);

            } else if ((data[i][1]).equals("offline")) {
                cir2.setStroke(Color.GRAY);
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.GREY));
                c = new Circle(5, javafx.scene.paint.Color.GRAY);
            }
            lbl.setGraphic(c);
            HBox box = new HBox();
            box.setId("hbox-custom-ava");
            box.getChildren().addAll(cir2, lbl, c);
            avplayer.getItems().add(box);

        }
        ///////////////end //////////
        ///////top plater//////////
        for (int i = 0; i < player.length; i++) {
            Label lbl = new Label(data[i][0]);///get name 
            lbl.setId("topPlayer");
            //ImageView img=new ImageView(getClass().getResource("user.png").toString());
            Circle cir2 = new Circle(30, 30, 15);
            cir2.setStroke(Color.SEAGREEN);
            Image im = new Image("xoclient/user.png", false);
            cir2.setFill(new ImagePattern(im));
            cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            //lbl.setGraphic(img);
            Label lb2 = new Label(data[i][2]);
            lb2.setStyle("fx-background-radius: 20;"
                    + "-fx-text-fill:#fff;"
                    + "-fx-font-size: 12pt ;"
                    + "-fx-background-insets:0 0 1 0;"
                    + "-fx-font-weight: bold;");
            lb2.setId("score");
            HBox box = new HBox();
            Region r = new Region();
            HBox.setHgrow(r, Priority.ALWAYS);
            box.setId("hbox-custom-top");
            box.getChildren().addAll(cir2, lbl, r, lb2);
            topplayer.getItems().add(box);

        }
        //////////////end top player////////
        avplayer.setOnMouseClicked(event -> {
            String select_player = avplayer.getSelectionModel().getSelectedItem().getChildren().get(1).toString();
            selected_Player = "$" + select_player.split("[']")[1];
        });

    }

    public void refusedToPlay(String msg) {
        String opponentName = msg.split(",")[1];
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Refused");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family:Berlin Sans FB;"
                + "-fx-background-color:#ffffff;"
                + "-fx-text-fill: #FE4451;"
                + "-fx-background-radius: 15;"));
        alert.setHeaderText("Invitation Refused");
        alert.setContentText(opponentName + " Refused Your Invitation!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void offlineOpponent(String msg) {
        String opponentName = msg.split(",")[1];
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Offline");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family:Berlin Sans FB;"
                + "-fx-background-color:#ffffff;"
                + "-fx-text-fill: #FE4451;"
                + "-fx-background-radius: 15;"));
        alert.setHeaderText("Offline Opponent");
        alert.setContentText(opponentName + " Is Offline!");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void inGameOpponent(String msg) {
        String opponentName = msg.split(",")[1];
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("InGame");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family:Berlin Sans FB;"
                + "-fx-background-color:#ffffff;"
                + "-fx-text-fill: #FE4451;"
                + "-fx-background-radius: 15;"));
        alert.setHeaderText("InGame Opponent");
        alert.setContentText(opponentName + " Is Already in a Game !");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    public void invitedToPlay(String msg) {
        String opponentName = msg.split("[$]")[1];
        Alert alert = new Alert(AlertType.CONFIRMATION);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        ButtonBar buttonBar = (ButtonBar) alert.getDialogPane().lookup(".button-bar");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-font-family:Berlin Sans FB;"
                + "-fx-background-color:#ffffff;"
                + "-fx-text-fill: #FE4451;"
                + "-fx-background-radius: 15;"));
        alert.setTitle("Invitation");
        alert.setHeaderText("You Have Been Invited");
        alert.setContentText(opponentName + " Invite you to Play a Game !");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.YES) {
                client.sendMsg("$yes," + opponentName);
            } else if (rs == ButtonType.NO) {
                client.sendMsg("$no," + opponentName);
            }
        });
    }
    @FXML 
    public void play_game(String listplayer) throws IOException {
        /// FXMLFriendController controller= new FXMLFriendController();//move to global game scene

        String cleanGameInfo = listplayer.split("\\$yes,")[1];
        String[] player = (cleanGameInfo).split(",");
        for (String player1 : player) {
            System.out.println("-->" + player1);
        }
        //controller.send_data(player[0],player[1],player[2],player[3])

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
        // Create a controller instance
        GameGLobalController gameController = new GameGLobalController(primaryStage, username, player[0], player[1].charAt(0), player[2], player[3].charAt(0));

        // Set it in the FXMLLoader
        loader.setController(gameController);
        primaryStage.setTitle("XO Global Game");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);              
    }

    @FXML
    private void closescene(MouseEvent event) {
//        Stage stage = (Stage) close.getScene().getWindow();
//        stage.close();
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void ask_player(ActionEvent event) {
        System.out.println("Username : " + username + " , Selected : " + selected_Player);
        if (selected_Player != null && !selected_Player.equals("$" + username)) {
            System.out.println("Selected Player : " + selected_Player);
            client.sendMsg(selected_Player);
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
        client.sendMsg("get info");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        client.sendMsg("logout");
        client.end();
        client=null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        // Create a controller instance
        LoginController loginController = new LoginController(primaryStage);
        // Set it in the FXMLLoader
        loader.setController(loginController);
        primaryStage.setTitle("XO Login");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
    }

}
