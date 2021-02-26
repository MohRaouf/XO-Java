/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author mohamedbassiouny
 */
public class MultiPlayerController implements Initializable {
    private Stage primaryStage;
    @FXML
    private TextField player1Name;
    @FXML
    private Text firstWarning;
    @FXML
    private ToggleButton player1XButton;
    @FXML
    private ToggleButton player1OButton;
    @FXML
    private TextField player2Name;
    @FXML
    private Text secondWarning;
    @FXML
    private ToggleButton player2XButton;
    @FXML
    private ToggleButton player2OButton;
    @FXML
    private BorderPane parent;
      /**
     *
     */
    public MultiPlayerController(Stage _primaryStage){
        primaryStage=_primaryStage;
    }
    @FXML
    private void handleNextAction(ActionEvent event) throws Exception  {
        String firstPlayerName=player1Name.getText();
        String secondPlayerName=player2Name.getText();
        String symbol;
        firstWarning.setVisible(false);
        secondWarning.setVisible(false);
        if(firstPlayerName.length()<3){
            showErrorMessage(firstWarning,"please enter player 1 name");
             return;
        }
        else if( !player1XButton.selectedProperty().getValue()&&!player1OButton.selectedProperty().getValue()){
            showErrorMessage(firstWarning,"please select player 1 Symbol");
             return;
        }
        if(secondPlayerName.length()<3){
            showErrorMessage(secondWarning,"please enter player 2 name");
             return;
        }
        
        else if( !player2XButton.selectedProperty().getValue()&&!player2OButton.selectedProperty().getValue()){
             showErrorMessage(secondWarning,"please select player 2 symbol");
             return;
        }
            //send data to Game Logic Class 
//            Player.player1Name=firstPlayerName;
//            Player.player2Name=secondPlayerName;
//            Player.player1Symbol='X';
//            Player.player2Symbol='O';
//           // Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));  
//            GameFriendController controller= new GameFriendController();
//            FXMLLoader  loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
//            loader.setController(controller);
//            Parent root2 = loader.load();
//            parent.getChildren().add(root2);
    }
    @FXML
    private void handleXButtonsPlayer1(ActionEvent event){
        handleVisablity(player2XButton);
        firstWarning.setVisible(false);
    }
    @FXML
    private void handleOButtonsPlayer1(ActionEvent event){
        handleVisablity(player2OButton);
        firstWarning.setVisible(false);
    }
    @FXML
    private void handleOButtonsPlayer2(ActionEvent event){
        handleVisablity(player1OButton);
        secondWarning.setVisible(false);
    }
    @FXML
    private void handleXButtonsPlayer2(ActionEvent event){
        handleVisablity(player1XButton);
        secondWarning.setVisible(false);
    }
    private void showErrorMessage(Text messageContainer,String message){
        messageContainer.setText(message);
        messageContainer.setVisible(true);
    }
    private void handleVisablity(ToggleButton button){
       if(!button.disableProperty().getValue())
            button.setDisable(true);
        else
            button.setDisable(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
