/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_data;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private ListView<Label> topplayer;
    @FXML
    private ListView<HBox> avplayer;
    @FXML
    private TextField tf;
    @FXML
    private FontAwesomeIcon close;
    @FXML
    private Button connect;
    @FXML
    private Button ask;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void showInformation(String list) throws FileNotFoundException {
        String[] player = (list).split("[,]");
        String[][] data = new String[20][3];
        //String[] player1=player[0].split("[|]");
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
        avplayer.setOnMouseClicked(event -> {
            String select_player = avplayer.getSelectionModel().getSelectedItem().getChildren().get(1).toString();
            tf.setText(select_player.split("[']")[1]);//split string name from labal class
        });

    }

    public void ask_to_play(String confirm) {

        Alert a = new Alert(AlertType.NONE);
        // set alert type 
        a.setAlertType(AlertType.CONFIRMATION);
       a.initStyle(StageStyle.UNDECORATED);
        // set content text 
        a.setContentText("Confirmation To play with: "+confirm);
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 16px; "
            + "-fx-font-weight: bold;"
                +"-fx-background-color: #442c2e;"
                + " -fx-text-fill:#ffffff;");
        GridPane grid = (GridPane) dialogPane.lookup(".header-panel");
        grid.setStyle("-fx-background-color: #feeae6; "
                + "-fx-font-style: italic;");
       ButtonBar buttonBar = (ButtonBar)a.getDialogPane().lookup(".button-bar");
    buttonBar.setStyle("-fx-font-size: 14px;"
            + "-fx-background-color: #feeae6;");
    buttonBar.getButtons().forEach(b->b.setStyle("-fx-background-color: #442c2e;"
            + "-fx-text-fill:#ffffff;\n" +
             "-fx-background-radius: 20;"));
        if (a.showAndWait().get() == ButtonType.OK) {
            a.setContentText("accept to play with:"+confirm);
            a.show();
        }
        // show the dialog 
        //a.show();
    }

    @FXML
    private void closescene(MouseEvent event) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void connect_to_play(ActionEvent event) {
    }

    @FXML
    private void ask_player(ActionEvent event) {
    }

}
