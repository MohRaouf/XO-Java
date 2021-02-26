package xoclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import localplayersmenu.Player;
//import localplayersmenu.GameLogic;

/**
 *
 * @author ITI
 */
public class GameFriendController implements Initializable {
    Stage primaryStage;
    String player1Name;
    char player1Pattern;
    String player2Name;
    char player2Pattern;
    int X_or_O = 0;
    int row, column;
    int oneArrayIndex;
    int rowComp = 0;
    int colComp = 0;
    int Winner = 0;
    boolean PlayAgain = true;
    int[] intArray;
    GameLogic Game;
    @FXML
    public Label play, player1Lb, player2Lb, Pattern1, Pattern2, score1, score2;
    public GridPane GridpaneForButton;
    public Button PlayButton;
    public ImageView celebratedImg, cupOfwinner;
    public AnchorPane mainPane;

    public GameFriendController(Stage _primaryStage, String player1Name, char player1Pattern, String player2Name, char player2Pattern) {
        this.player1Name=player1Name;
        this.player2Name=player2Name;
        this.player1Pattern=player1Pattern;
        this.player2Pattern=player2Pattern;
        this.primaryStage=_primaryStage;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if (PlayAgain == false) {
                if (Winner == 0) {

                    Button SelectedButton = (Button) event.getSource();
                    row = GridPane.getRowIndex(SelectedButton);
                    column = GridPane.getColumnIndex(SelectedButton);
                    oneArrayIndex = (row) * 3 + column;

                    if ("".equals(SelectedButton.getText())) {
                        if (X_or_O == 0) {
                            SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
                            SelectedButton.setText(Character.toString(Game.player1symbol));
                            Winner = Game.checkWinner(1, oneArrayIndex);
                            System.out.println(Winner);
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText(Game.player2 + " turn");
                            X_or_O = 1;
                        } else if (X_or_O == 1) {
                            SelectedButton.setTextFill(Color.valueOf(Game.Player2Color));
                            SelectedButton.setText(Character.toString(Game.player2symbol));
                            Winner = Game.checkWinner(2, oneArrayIndex);
                            System.out.println(Winner);
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1 + " turn");
                            X_or_O = 0;
                        }
                    }

                    if (Winner != 0) {
                        switch (Winner) {
                            case 1:
                                play.setTextFill(Color.valueOf(Game.Player1Color));
                                play.setText(Game.player1 + " is the Winner");
                                score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                                celebratedImg.setVisible(true);
                                cupOfwinner.setVisible(true);
                                break;
                            case 2:
                                play.setTextFill(Color.valueOf(Game.Player2Color));
                                play.setText(Game.player2 + " is the Winner");
                                score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                                celebratedImg.setVisible(true);
                                cupOfwinner.setVisible(true);
                                break;
                            case 3:
                                play.setText("There is no Winner");
                                break;
                        }
                        PlayAgain = true;
                        PlayButton.setDisable(false);
                    }
                }
            }

        } catch (Exception ex) {
        }
    }

    @FXML
    private void handlePlayAction(ActionEvent event) {
        if (PlayAgain == true) {
            celebratedImg.setVisible(false);
            cupOfwinner.setVisible(false);
            play.setTextFill(Color.valueOf(Game.Player1Color));
            play.setText(Game.player1 + " turn");
            if (Winner == 1) {
                X_or_O = 0;
                play.setTextFill(Color.valueOf(Game.Player1Color));
                play.setText(Game.player1 + " turn");
            } else if (Winner == 2) {
                X_or_O = 1;
                play.setTextFill(Color.valueOf(Game.Player2Color));
                play.setText(Game.player2 + " turn");
            }
            PlayAgain = false;
            Winner = 0;
            PlayButton.setDisable(true);
            GridpaneForButton.getChildren().forEach((node) -> {
                ((Button) node).setText("");
            });
            Game = new GameLogic(player1Name, player2Name, player1Pattern, player2Pattern);
        }

    }
 @FXML
     private void back(ActionEvent event) throws IOException {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiPlayer.fxml"));
                    // Create a controller instance
                    MultiPlayerController multiplayerController = new MultiPlayerController(primaryStage);
                    // Set it in the FXMLLoader
                    loader.setController(multiplayerController);
                    primaryStage.setTitle("XO Dashboard");
                    Scene scene = new Scene((Parent) loader.load());
                    primaryStage.setScene(scene);
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(player1Name, player2Name, player1Pattern, player2Pattern);

        GameLogic.scoreOfPlayer1 = 0;
        GameLogic.scoreOfPlayer2 = 0;
        player1Lb.setText(Game.player1);
        player2Lb.setText(Game.player2);
        Pattern1.setTextFill(Color.valueOf(Game.Player1Color));
        Pattern2.setTextFill(Color.valueOf(Game.Player2Color));
        score1.setTextFill(Color.valueOf(Game.Player1Color));
        score2.setTextFill(Color.valueOf(Game.Player2Color));
        Pattern1.setText(Character.toString(Game.player1symbol));
        Pattern2.setText(Character.toString(Game.player2symbol));
        score1.setText(Integer.toString(GameLogic.scoreOfPlayer1));
        score2.setText(Integer.toString(GameLogic.scoreOfPlayer2));
    }

}
