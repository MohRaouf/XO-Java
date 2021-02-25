/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author esraa abou alkassem
 */
public class Sce2Controller implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void login_user(String user) {
        Circle cir2 = new Circle(40, 40, 20);
        cir2.setStroke(Color.DARKSLATEBLUE);
        Image im = new Image("show_data/user.png", false);
        cir2.setFill(new ImagePattern(im));
        cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.CORNSILK));
        Label lbName = new Label(user);///get name 
        lbName.setGraphic(cir2);
        loginuser.getChildren().addAll(lbName);
        loginuser.setStyle("visibility: visible;");
    }

    public void showInformation(String list) throws FileNotFoundException {
        String[] player = (list).split("[,]");
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
            if ((data[i][1]).equals("online"))//get status
            {
                Label lbl = new Label(data[i][0]);///get name 
                //ImageView img=new ImageView(getClass().getResource("user.png").toString());
                Circle cir2 = new Circle(25, 25, 11);
                cir2.setStroke(Color.SEAGREEN);
                Image im = new Image("show_data/user.png", false);
                cir2.setFill(new ImagePattern(im));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
                //lbl.setGraphic(img);
                Circle c = new Circle(5, javafx.scene.paint.Color.GREEN);
                lbl.setGraphic(c);
                HBox box = new HBox();
                box.getChildren().addAll(cir2, lbl, c);
                avplayer.getItems().add(box);

            } else if ((data[i][1]).equals("offline")) {
                Label lbl = new Label(data[i][0]);
                //ImageView img=new ImageView(getClass().getResource("user.png").toString());
                Circle cir2 = new Circle(25, 25, 11);
                cir2.setStroke(Color.GRAY);
                Image im = new Image("show_data/user.png", false);
                cir2.setFill(new ImagePattern(im));
                cir2.setEffect(new DropShadow(+25d, 0d, +2d, Color.GREY));
                //lbl.setGraphic(img);
                Circle c = new Circle(5, javafx.scene.paint.Color.GRAY);
                lbl.setGraphic(c);
                HBox box = new HBox();
                box.getChildren().addAll(cir2, lbl, c);
                avplayer.getItems().add(box);

            }

        }
        ///////////////end //////////
        ///////top plater//////////
        for (int i = 0; i < player.length; i++) {
            Label lbl = new Label(data[i][0]);///get name 
            //ImageView img=new ImageView(getClass().getResource("user.png").toString());
            Circle cir2 = new Circle(25, 25, 11);
            cir2.setStroke(Color.SEAGREEN);
            Image im = new Image("show_data/user.png", false);
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
            tf.setText(select_player.split("[']")[1]);//split string name from labal class
        });

    }

    public void ask_to_play(String confirm) {

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
            a.setContentText("accept to play with:" + confirm);
            String accept_to_play = "$yes," + confirm;
            System.out.println(accept_to_play);
            a.show();
        }
        // show the dialog 
        //a.show();
    }

    public void play_game(String listplayer) {
        /// FXMLFriendController controller= new FXMLFriendController();//move to global game scene
        String[] player = (listplayer).split("[,]");
        for (int i = 0; i < player.length; i++) {
            System.out.println(player[i]);
        }
        //controller.send_data(player[0],player[1],player[2],player[3])
    }

    @FXML
    private void closescene(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void ask_player(ActionEvent event) {
        loader.setStyle("visibility: visible;");
    }

    @FXML
    private void rrefresh_sceen(ActionEvent event) {
    }

}
