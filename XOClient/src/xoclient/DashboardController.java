/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.shape.Circle;
import static xoclient.XOClient.client;

/**
 * FXML Controller class
 *
 * @author esraa abou alkassem
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane ap2;
    private TextField tf2;
    @FXML
    private ListView<HBox> topplayer;
    @FXML
    private ListView<HBox> avplayer;
    @FXML
    private TextField tf;
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

    }

    public void login_user(String user) {
        Circle cir2 = new Circle(40, 40, 20);
        cir2.setStroke(Color.DARKSLATEBLUE);
        Image im = new Image("xoclient/user.png", false);
        cir2.setFill(new ImagePattern(im));
        cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.CORNSILK));
        Label lbName = new Label(user);///get name 
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
        for (int i = 0; i < player.length; i++) {
            Label lbl = new Label(data[i][0]);///get name 
            //ImageView img=new ImageView(getClass().getResource("user.png").toString());
            Circle cir2 = new Circle(25, 25, 11);
            Image im = new Image("xoclient/user.png", false);
            cir2.setFill(new ImagePattern(im));
            Circle c=null;
            if ((data[i][1]).equals("online"))//get status
            {
                cir2.setStroke(Color.SEAGREEN);
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.SEAGREEN));
                c = new Circle(5, javafx.scene.paint.Color.SEAGREEN);

            } else if ((data[i][1]).equals("offline")) {
                cir2.setStroke(Color.GRAY);
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.GREY));
                c = new Circle(5, javafx.scene.paint.Color.GRAY);
            }
            lbl.setGraphic(c);
            HBox box = new HBox();
            box.getChildren().addAll(cir2, lbl, c);
            avplayer.getItems().add(box);

        }
        ///////////////end //////////
        ///////top plater//////////
        for (int i = 0; i < player.length; i++) {
            Label lbl = new Label(data[i][0]);///get name 
            //ImageView img=new ImageView(getClass().getResource("user.png").toString());
            Circle cir2 = new Circle(25, 25, 11);
            cir2.setStroke(Color.SEAGREEN);
            Image im = new Image("xoclient/user.png", false);
            cir2.setFill(new ImagePattern(im));
            cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            //lbl.setGraphic(img);
            Label lb2 = new Label(data[i][2]);
            lb2.setStyle("-fx-background-color: #442c2e;"
                    + "fx-background-radius: 20;"
                    + "-fx-text-fill:#ffffff;"
                    + "-fx-background-insets:0 0 1 0;"
                    + "-fx-padding:0 0 10 0;"
                    + "-fx-font-weight: bold;");
            Circle c = new Circle(5, javafx.scene.paint.Color.GREEN);
            lbl.setGraphic(c);
            HBox box = new HBox();
            box.getChildren().addAll(cir2, lbl, c, lb2);
            topplayer.getItems().add(box);

        }
        //////////////end top player////////
        avplayer.setOnMouseClicked(event -> {
            String select_player = avplayer.getSelectionModel().getSelectedItem().getChildren().get(1).toString();
            selected_Player = "$" + select_player.split("[']")[1];

//            tf.setText(select_player.split("[']")[1]);//split string name from labal class
        });

    }

    public void ask_to_play(String msg) {
        System.out.println(msg);
        String confirm = msg.split("[$]")[1];
        Alert a = new Alert(AlertType.NONE);
        // set alert type 
        a.setAlertType(AlertType.CONFIRMATION);
        // set content text 
        a.initStyle(StageStyle.TRANSPARENT);
        a.setContentText("Confirmation To play with: " + confirm);
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; "
                + "-fx-font-weight: bold;"
                + "-fx-background-color: #f7f6e7;"
                + "-fx-text-fill:#03506f;"
                + "-fx-border-color:#fb743e;"
                + "-fx-border-width: 5;");
        GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
        grid.setStyle("-fx-background-color: #f7f6e7; "
                + "-fx-font-style: italic;");
        ButtonBar buttonBar = (ButtonBar) a.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-font-size: 14px;"
                + "-fx-background-color: #f7f6e7;");
        buttonBar.getButtons().forEach(b -> b.setStyle("-fx-background-color: #fb743e;"
                + "-fx-text-fill:#ffffff;\n"
                + "-fx-background-radius: 20;"));
        if (a.showAndWait().get() == ButtonType.OK) {
            a.setContentText("Accept To Play with:" + confirm);
            String accept_to_play = "$yes," + confirm;
            client.sendMsg("$yes," + confirm);
            System.out.println(accept_to_play);
        }
        // show the dialog 
        //a.show();
    }

    public void play_game(String listplayer) {
        /// FXMLFriendController controller= new FXMLFriendController();//move to global game scene

        String cleanGameInfo = listplayer.split("$yes,")[1];
        String[] player = (cleanGameInfo).split("[,]");
        for (int i = 0; i < player.length; i++) {
            System.out.println(player[i]);
        }
        //controller.send_data(player[0],player[1],player[2],player[3])
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
//                    // Create a controller instance
//                    DashboardController dashboardController = new DashboardController(primaryStage,playerName);
//                    // Set it in the FXMLLoader
//                    loader.setController(dashboardController);
//                    primaryStage.setTitle("XO Dashboard");
//                    Scene scene = new Scene((Parent) loader.load());
//                    primaryStage.setScene(scene);
    }

    @FXML
    private void closescene(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void ask_player(ActionEvent event) {
        if (selected_Player != null) {
            System.out.println("Selected Player : " + selected_Player);
            client.sendMsg(selected_Player);
        }

        loader.setStyle("visibility: visible;");

    }

    @FXML
    private void rrefresh_sceen(ActionEvent event) {
    }

}
