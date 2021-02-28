/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.net.*;
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
import static xoclient.XOClient.client;

/**
 *
 * @author ITI
 */
public class GameGLobalController implements Initializable {

    int row, column;
    int index, oneArrayIndex;
    int rowComp = 0;
    int colComp = 0;
    int Winner = 0;
    boolean PlayAgain = true;
    int[] intArray;
    boolean waitTurn = false;
    String Received;
    boolean startGame = false;
    GameLogic Game;
    Socket mySocket;
    boolean waitToChange = false;
    Stage primaryStage2;
    String Player1Name, Player2Name, userName;
    char Player1Pattern, Player2Pattern;
    DataInputStream Datais;
    PrintStream ps;
    @FXML
    public Label play, player1Lb, player2Lb, Pattern1, Pattern2, score1, score2;
    public GridPane GridpaneForButton;
    public Button PlayButton,backButton,recordButton;
    public ImageView celebratedImg, cupOfwinner, LoseImage;
    public AnchorPane mainPane;

    public GameGLobalController(Stage _primaryStage, String username, String Player1N, char Player1Pattern, String Player2N, char Player2Pattern) {
        primaryStage2 = _primaryStage;
        client.gameController = this;
        this.Player1Name = Player1N;
        this.Player2Name = Player2N;
        this.Player1Pattern = Player1Pattern;
        this.Player2Pattern = Player2Pattern;
        this.userName = username;
        Game = new GameLogic(this.userName, this.Player1Name, this.Player2Name, this.Player1Pattern, this.Player2Pattern);
        System.out.println(Game.youNumber + "+" + userName);
        recordButton.setVisible(false);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {

            if (Winner == 0) {

                Button SelectedButton = (Button) event.getSource();
                row = GridPane.getRowIndex(SelectedButton);
                column = GridPane.getColumnIndex(SelectedButton);
                oneArrayIndex = (row) * 3 + column;
                if ("".equals(SelectedButton.getText())) {
                    if (Game.youNumber == 1 && PlayAgain == false && waitTurn == false) {
                        DrawOnButton(SelectedButton, Game.youNumber);
                        Winner = Game.checkWinner(1, oneArrayIndex);
                        client.sendMsg(">" + oneArrayIndex);
                        waitTurn = true;
                        //  ps.println(">"+oneArrayIndex);
                        Received = "";
                        WinnerAction();
                    } else if (Game.youNumber == 2) {
                        if (waitTurn == true) {
                            DrawOnButton(SelectedButton, Game.youNumber);
                            Winner = Game.checkWinner(2, oneArrayIndex);
                            client.sendMsg(">" + oneArrayIndex);
                            waitTurn = false;
                            WinnerAction();
                        }
                    }
                }

            }

        } catch (Exception ex) {
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        // Create a controller instance
        DashboardController dashboardController = new DashboardController(primaryStage2, userName);
        // Set it in the FXMLLoader
        loader.setController(dashboardController);
        primaryStage2.setTitle("XO Dashboard");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage2.setScene(scene);
    }

    @FXML
    private void handlePlayAction(ActionEvent event) {
        startGame = true;
        PlayAgain = false;
        PlayButton.setDisable(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         score1.setVisible(false);
         score2.setVisible(false);
        //  Game = new GameLogic(userName, Player1Name, Player2Name, Player1Pattern, Player2Pattern);
        if (Game.youNumber == 2) {
            PlayButton.setDisable(true);
        }
        backButton.setDisable(true);
        GameLogic.scoreOfPlayer1 = 0;
        GameLogic.scoreOfPlayer2 = 0;
        player1Lb.setText(Game.player1);
        player2Lb.setText(Game.player2);
        Pattern1.setTextFill(Color.valueOf(Game.Player1Color));
        Pattern2.setTextFill(Color.valueOf(Game.Player2Color));
        /*score1.setTextFill(Color.valueOf(Game.Player1Color));
        score2.setTextFill(Color.valueOf(Game.Player2Color));*/
        Pattern1.setText(Character.toString(Game.player1symbol));
        Pattern2.setText(Character.toString(Game.player2symbol));
       /* score1.setText(Integer.toString(GameLogic.scoreOfPlayer1));
        score2.setText(Integer.toString(GameLogic.scoreOfPlayer2));*/
    }

    void DrawReceived(String Received) {
        char ReceivedIndex = Received.charAt(1);
        int Index = Integer.parseInt(String.valueOf(ReceivedIndex));
        index = Index;
        while (Index >= 3) {
            Index -= 3;
            rowComp++;
        }
        colComp = Index;

        System.out.println(rowComp + "--" + colComp);
        GridpaneForButton.getChildren().forEach((node) -> {
            if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {
                if ("".equals(((Button) node).getText())) {
                    if (Game.youNumber == 1) {
                        WinnerAction();
                        DrawOnButton(((Button) node), 2);
                        Winner = Game.checkWinner(2, index);
                        WinnerAction();
                        waitTurn = false;
                    } else if (Game.youNumber == 2) {
                        DrawOnButton(((Button) node), 1);
                        Winner = Game.checkWinner(1, index);
                        WinnerAction();
                        waitTurn = true;
                    }
                    System.out.println(rowComp + "**" + colComp);
                }

            }
        }
        );
        rowComp = 0;
        colComp = 0;
    }

    void WinnerAction() {
        if (Winner != 0) {
            switch (Winner) {
                case 1:
                    if (Game.youNumber == 1) {
                        System.out.println("player1 Winner");
                        play.setTextFill(Color.valueOf(Game.Player1Color));
                        play.setText(Game.player1 + " is the Winner");
                     //   score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                        celebratedImg.setVisible(true);
                        cupOfwinner.setVisible(true);
                        waitTurn = true;
                        client.sendMsg("=win");
                    } else {
                        System.out.println("player2 loose");
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(" You lose.....");
                        LoseImage.setVisible(true);
                        waitTurn = false;
                        client.sendMsg("=lose");
                    }
                    break;
                case 2:
                    if (Game.youNumber == 2) {
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(Game.player2 + " is the Winner");
                      //  score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                        celebratedImg.setVisible(true);
                        cupOfwinner.setVisible(true);
                        waitTurn = false;
                        client.sendMsg("=win");
                    } else {
                        play.setTextFill(Color.valueOf(Game.Player1Color));
                        play.setText(" You lose.....");
                        LoseImage.setVisible(true);
                        waitTurn = true;
                        client.sendMsg("=lose");
                    }
                    break;
                case 3:
                    play.setText("There is no Winner");
                    client.sendMsg("=draw");
                    break;
            }
            PlayAgain = true;
            backButton.setDisable(false);
        }
    }

    void DrawOnButton(Button btn, int yourNum) {
        switch (yourNum) {
            case 1:
                btn.setTextFill(Color.valueOf(Game.Player1Color));
                btn.setText(Character.toString(Game.player1symbol));
                play.setTextFill(Color.valueOf(Game.Player2Color));
                play.setText(Game.player2 + " turn");
                break;
            case 2:
                btn.setTextFill(Color.valueOf(Game.Player2Color));
                btn.setText(Character.toString(Game.player2symbol));
                play.setTextFill(Color.valueOf(Game.Player1Color));
                play.setText(Game.player1 + " turn");
                break;
        }
    }
}
/*  try{
       while(true){
        
    
         System.out.println("****"+index);
          Platform.runLater(() -> {
                  DrawReceived(index);
                        });  
         
          
       }}catch(IOException | NumberFormatException ex){
        }*/
