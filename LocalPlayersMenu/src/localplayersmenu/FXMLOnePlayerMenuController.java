/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oneplayermenu;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

/**
 *
 * @author mohamedbassiouny
 */
public class FXMLOnePlayerMenuController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TextField playerName;
    @FXML
    private Text warning;
    @FXML
    private ToggleButton XButton;
    @FXML
    private ToggleButton OButton;
    
    @FXML
        private void handleNextAction(ActionEvent event)   {
        String name=playerName.getText();
        warning.setVisible(false);
        if(name.length()<3){
            showErrorMessage(warning,"please enter player  name");
             return;
        }
        else if( !XButton.selectedProperty().getValue()&&!OButton.selectedProperty().getValue()){
            showErrorMessage(warning,"please select player  Symbol");
             return;
        }
            //send data to Game Logic Class             Player.player2Name=secondPlayerName;
    }
    private void showErrorMessage(Text messageContainer,String message){
        messageContainer.setText(message);
        messageContainer.setVisible(true);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
