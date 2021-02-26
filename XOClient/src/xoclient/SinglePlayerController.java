/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author mohamedbassiouny
 */
public class SinglePlayerController implements Initializable {

    public boolean hard = false;
    public boolean easy = false;
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
    
    private Stage primaryStage;
    
    public SinglePlayerController(Stage _primaryStage){
        primaryStage=_primaryStage;
    }

    @FXML
    private void handleNextAction(ActionEvent event) throws IOException {
//        String name = playerName.getText();
//        warning.setVisible(false);
//        if (name.length() < 3) {
//            showErrorMessage(warning, "please enter player  name");
//            return;
//        } else if (!XButton.selectedProperty().getValue() && !OButton.selectedProperty().getValue()) {
//            showErrorMessage(warning, "please select player  Symbol");
//            return;
//        } else if (OButton.selectedProperty().getValue()) {
//            Player.player1Symbol = 'o';
//
//        } else if (XButton.selectedProperty().getValue()) {
//            Player.player1Symbol = 'x';
//        }
//        Player.player1Name = name;
//        //send data to Game Logic Class             Player.player2Name=secondPlayerName;
//        Player.player2Name = "AI";
//        if (Player.player1Symbol == 'x') {
//            Player.player2Symbol = 'o';
//        } else {
//            Player.player2Symbol = 'x';
//        }
//        //send data to Game Logic Class             Player.player2Name=secondPlayerName;
//        if (hard) {
//            HardLevelController controller = new HardLevelController();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
//            loader.setController(controller);
//            Parent root2 = loader.load();
//            parent.getChildren().add(root2);
//        }
//        if (easy) {
//            GameComputerController controller = new GameComputerController();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
//            loader.setController(controller);
//            Parent root2 = loader.load();
//            parent.getChildren().add(root2);
//        }
//        else{
//            System.out.println("please Select level");
//        }
        //Parent root2 = FXMLLoader.load(getClass().getResource("FXMLGameDocument.fxml"));
        //      parent.getChildren().add(root2);

        /*(hardController controller = new hardController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
        loader.setController(controller);
        Parent root2 = loader.load();
        parent.getChildren().add(root2);*/
    }
    @FXML
    private void hardLevel(ActionEvent event) {
        hard = !hard;
    }

    @FXML
    private void easyLevel(ActionEvent event) {
        easy = !easy;
    }

    private void showErrorMessage(Text messageContainer, String message) {
        messageContainer.setText(message);
        messageContainer.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
