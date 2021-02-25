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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
         //Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));  
        ///make root to scene of game root2222
        Sce2Controller scencontroller = loader.getController();
        //String s="@"+tf.getText()+"|online|200";
          scencontroller.login_user("esraa");
          ///////show info
        if (tf.getText().contains("@")) {
            String[] list = (tf.getText()).split("[@]");
            String listplayer = list[1];
            System.out.println(listplayer);
            scencontroller.showInformation(listplayer);
            ap1.getChildren().add(root);
             } 
        ////////////// accept to play
        else if (tf.getText().startsWith("$yes")) {
            String[] list = (tf.getText()).split("[$]yes,");
            String listplayer = list[1];
            ap1.getChildren().add(root);
            System.out.println(listplayer);
            scencontroller.play_game(listplayer);
            
        }
        ///////////// ask to play
        else if (tf.getText().contains("$")) {
            ap1.getChildren().add(root);
            String[] list = (tf.getText()).split("[$]");
            String listplayer = list[1];
            System.out.println(listplayer);
            scencontroller.ask_to_play(listplayer);

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
