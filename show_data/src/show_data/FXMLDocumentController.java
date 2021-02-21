/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package show_data;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author esraa abou alkassem
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private AnchorPane ap1;
    @FXML
    private Button sendbtn;
    @FXML
    private TextField tf;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sce2.fxml"));
        Parent root = loader.load();
        Sce2Controller scencontroller = loader.getController();
        //String s="@"+tf.getText()+"|online|200";

        if (tf.getText().contains("@")) {
            String[] list = (tf.getText()).split("[@]");
            String listplayer = list[1];
            System.out.println(listplayer);
//            String[] player=(listplayer).split("[,]");
//            for(int i=0;i<player.length;i++){
//             
//            
//            }
            //String[] arrOfStr = (listplayer).split("[|]");
            scencontroller.showInformation(listplayer);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("window scene2");
            stage.show();

        } else if (tf.getText().contains("$")) {
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("window scene2");
            stage.show();
            String[] list = (tf.getText()).split("[$]");
            String listplayer = list[1];
            System.out.println(listplayer);
            scencontroller.ask_to_play(listplayer);

        }

//        Stage stage=new Stage();
//        stage.setScene(new Scene(root));
//        stage.initStyle(StageStyle.UNDECORATED);
//        stage.setTitle("window scene2");
//        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
