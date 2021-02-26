/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.net.*;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public Button PlayButton;
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
        System.out.println(Game.youNumber+"+"+userName);
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
                    if (Game.youNumber == 1 && PlayAgain == false  && waitTurn==false) {
                        SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
                        SelectedButton.setText(Character.toString(Game.player1symbol));
                        Winner = Game.checkWinner(1, oneArrayIndex);
                        System.out.println(Winner);
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(Game.player2 + " turn");
                        client.sendMsg(">" + oneArrayIndex);
                        
                        waitTurn=true;
                        //  ps.println(">"+oneArrayIndex);
                        Received = "";
                        checkTheWinner();
                    } else if (Game.youNumber == 2) {
                        if (waitTurn == true) {
                            SelectedButton.setTextFill(Color.valueOf(Game.Player2Color));
                            SelectedButton.setText(Character.toString(Game.player2symbol));
                            Winner = Game.checkWinner(2, oneArrayIndex);
                             
                            System.out.println("check player 2 status "+Winner);
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1 + " turn");
                            // ps.println(">"+oneArrayIndex);
                            client.sendMsg(">" + oneArrayIndex);
                            
                            waitTurn = false;
                            checkTheWinner();
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
                    DashboardController dashboardController = new DashboardController(primaryStage2,userName);
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

        /*   if(PlayAgain==true){
          celebratedImg.setVisible(false);
          cupOfwinner.setVisible(false);
        play.setTextFill(Color.valueOf(Game.Player1Color));
       play.setText(Game.player1+" turn");
      intArray = new int[]{ 0,1,2,3,4,5,6,7,8 };
      if(Winner==1)
      {  X_or_O = 0;
      play.setTextFill(Color.valueOf(Game.Player1Color));
      play.setText(Game.player1+" turn");}
      else if(Winner==2)
      { X_or_O = 1;
      play.setTextFill(Color.valueOf(Game.Player2Color));
       play.setText(Game.player2+" turn");}
        PlayAgain=false;
         Winner=0;
         PlayButton.setDisable(true);
         GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
            });
         Game = new GameLogic(Player.player1Name, Player.player2Name,Player.player1Symbol,Player.player2Symbol);
        }*/
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
      //  Game = new GameLogic(userName, Player1Name, Player2Name, Player1Pattern, Player2Pattern);
         
        if (Game.youNumber == 2) {
            PlayButton.setDisable(true);
        }

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
                    if (Game.youNumber == 1 ) {
                        checkTheWinner();
                        ((Button) node).setTextFill(Color.valueOf(Game.Player2Color));
                        ((Button) node).setText(Character.toString(Game.player2symbol));
                        play.setTextFill(Color.valueOf(Game.Player1Color));
                        play.setText(Game.player1 + " turn");
                        Winner = Game.checkWinner(2, index);
                        checkTheWinner();
                        waitTurn=false;
                    } else if (Game.youNumber == 2) {
                        
                        ((Button) node).setTextFill(Color.valueOf(Game.Player1Color));
                        ((Button) node).setText(Character.toString(Game.player1symbol));
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(Game.player2 + " turn");
                        Winner = Game.checkWinner(1, index);
                        checkTheWinner();
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

    void checkTheWinner() {
        if (Winner != 0) {
            switch (Winner) {
                case 1:
                    if (Game.youNumber == 1) {
                        System.out.println("player1 Winner");
                        play.setTextFill(Color.valueOf(Game.Player1Color));
                        play.setText(Game.player1 + " is the Winner");
                        score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                        celebratedImg.setVisible(true);
                        cupOfwinner.setVisible(true);
                         waitTurn=true;
                         client.sendMsg("=win");
                    } else {
                        System.out.println("player2 loose");
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(" You lose.....");
                        LoseImage.setVisible(true);
                        waitTurn=false;
                        client.sendMsg("=lose");
                    }
                    break;
                case 2:
                    if (Game.youNumber == 2) {
                        play.setTextFill(Color.valueOf(Game.Player2Color));
                        play.setText(Game.player2 + " is the Winner");
                        score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                        celebratedImg.setVisible(true);
                        cupOfwinner.setVisible(true);
                        waitTurn=false;
                        client.sendMsg("=win");
                    } else {
                        play.setTextFill(Color.valueOf(Game.Player1Color));
                        play.setText(" You lose.....");
                        LoseImage.setVisible(true);
                        waitTurn=true;
                        client.sendMsg("=lose");
                    }
                    break;
                case 3:
                    play.setText("There is no Winner");
                    client.sendMsg("=draw");
                    break;
            }
            PlayAgain = true;
            PlayButton.setDisable(false);
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
