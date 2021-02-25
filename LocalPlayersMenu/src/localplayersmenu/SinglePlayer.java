/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localplayersmenu;

import java.io.IOException;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 *
 * @author mohamedbassiouny
 */
public class SinglePlayer implements Initializable {
    
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
    private BorderPane parent;
    @FXML
        private void handleNextAction(ActionEvent event) throws IOException   {
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
            GameGLobalController controller= new GameGLobalController();
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
            loader.setController(controller);
            Parent root2 = loader.load();
            parent.getChildren().add(root2);
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
