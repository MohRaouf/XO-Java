/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.io.*;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import localplayersmenu.Player;

/**
 *
 * @author ITI
 */
public class HardLevelController implements Initializable {

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
    Minimax Ai;

    @FXML
    public Label play, player1Lb, player2Lb, Pattern1, Pattern2, score1, score2;
    public GridPane GridpaneForButton;
    public Button PlayButton;
    public ImageView celebratedImg, cupOfwinner;
    public AnchorPane mainPane;

    HardLevelController(Stage _primaryStage, String player1Name, char player1Pattern, String player2Name, char player2Pattern) {
        this.player1Name = player1Name;
        this.player1Pattern = player1Pattern;
        this.player2Name = player2Name;
        this.player2Pattern = player2Pattern;
        primaryStage = _primaryStage;
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
                        SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
                        SelectedButton.setText(Character.toString(Game.player1symbol));
                        Winner = Game.checkWinner(1, oneArrayIndex);
                        System.out.println(oneArrayIndex + "///" + Winner);
                        if (Winner == 0) {
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText("Computer turn");
                            int Index = Ai.findBestMove(Game.MatrixOfXO);
                            int prevIndex = Index;
                            System.out.println(Index);
                            while (Index >= 3) {
                                Index -= 3;
                                rowComp++;
                            }
                            colComp = Index;
                            System.out.println(rowComp + "--" + colComp);
                            GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {
                                    ((Button) node).setTextFill(Color.valueOf(Game.Player2Color));
                                    ((Button) node).setText(Character.toString(Game.player2symbol));
                                    System.out.println(rowComp + "**" + colComp);
                                }
                            });
                            Winner = Game.checkWinner(2, prevIndex);
                            rowComp = 0;
                            colComp = 0;
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1 + " turn");
                        }
                    }

                  WinnerAction();
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
            intArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
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
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SinglePlayer.fxml"));
        SinglePlayerController singlePlayerController = new SinglePlayerController(primaryStage);
        loader.setController(singlePlayerController);
        primaryStage.setTitle("XO Dashboard");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(player1Name, player2Name, player1Pattern, player2Pattern);
        Ai = new Minimax();
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
      void WinnerAction() {

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
