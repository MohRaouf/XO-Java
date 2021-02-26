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
import javafx.scene.Scene;
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

    char player1Pattern;
    char player2Pattern;
    String name;
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

    public SinglePlayerController(Stage _primaryStage) {
        primaryStage = _primaryStage;
    }

    @FXML
    private void handleNextAction(ActionEvent event) throws IOException {
        name = playerName.getText();
        warning.setVisible(false);
        if (name.length() < 3) {
            showErrorMessage(warning, "please enter player  name");
            return;
        } else if (!XButton.selectedProperty().getValue() && !OButton.selectedProperty().getValue()) {
            showErrorMessage(warning, "please select player  Symbol");
            return;
        } else if (OButton.selectedProperty().getValue()) {
            player1Pattern = 'o';

        } else if (XButton.selectedProperty().getValue()) {
            player1Pattern = 'x';
        }

        //send data to Game Logic Class             Player.player2Name=secondPlayerName;
        if (player1Pattern == 'x') {
            player2Pattern = 'o';
        } else {
            player2Pattern = 'x';
        }
        //send data to Game Logic Class             Player.player2Name=secondPlayerName;
        if (hard) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            HardLevelController hardLevelController = new HardLevelController(primaryStage, name, player1Pattern, "Computer", player2Pattern);
            loader.setController(hardLevelController);
            primaryStage.setTitle("Game");
            Scene scene = new Scene((Parent) loader.load());
            primaryStage.setScene(scene);
        }
        if (easy) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameScreen.fxml"));
            GameComputerController gameComputerController = new GameComputerController(primaryStage, name, player1Pattern, "Computer", player2Pattern);
            loader.setController(gameComputerController);
            primaryStage.setTitle("Game");
            Scene scene = new Scene((Parent) loader.load());
            primaryStage.setScene(scene);
        } else {
            System.out.println("please Select level");
        }
//        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLGameDocument.fxml"));
//        parent.getChildren().add(root2);

//        hardController controller = new hardController(name,);
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGameDocument.fxml"));
//        loader.setController(controller);
//        Parent root2 = loader.load();
//        parent.getChildren().add(root2);
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
