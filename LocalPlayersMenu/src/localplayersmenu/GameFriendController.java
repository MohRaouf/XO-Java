package localplayersmenu;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import localplayersmenu.Player;
import localplayersmenu.GameLogic;
/**
 *
 * @author ITI
 */

public class GameFriendController implements Initializable {

    int X_or_O = 0;int row,column;int oneArrayIndex;int rowComp=0;int colComp=0;
    int Winner = 0;boolean PlayAgain=true; int[] intArray ;
  GameLogic Game ;
    @FXML
   public Label play,player1Lb,player2Lb,Pattern1,Pattern2,score1,score2;
   public GridPane GridpaneForButton;
   public Button PlayButton;
   public ImageView celebratedImg,cupOfwinner;
   public AnchorPane mainPane;
    @FXML
    private void handleButtonAction(ActionEvent event) {
       try {
            if ( PlayAgain==false) {
                if( Winner == 0 ){
                    
                Button SelectedButton = (Button) event.getSource();
                       row = GridPane.getRowIndex(SelectedButton);
                         column = GridPane.getColumnIndex(SelectedButton);
                          oneArrayIndex = (row)*3 + column;
                         
                  if ("".equals(SelectedButton.getText())) {
               if (X_or_O == 0) { 
                      SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
                        SelectedButton.setText(Character.toString(Game.player1symbol));  
                    Winner=Game.checkWinner(1,oneArrayIndex);
                    System.out.println(Winner);
                      play.setTextFill(Color.valueOf(Game.Player2Color));
                    play.setText(Game.player2+" turn"); 
                    X_or_O = 1;
                } else if (X_or_O == 1) {
                      SelectedButton.setTextFill(Color.valueOf(Game.Player2Color));
                   SelectedButton.setText(Character.toString(Game.player2symbol));  
                   Winner=Game.checkWinner(2,oneArrayIndex);
                   System.out.println(Winner);
                      play.setTextFill(Color.valueOf(Game.Player1Color));
                    play.setText(Game.player1+" turn"); 
                    X_or_O = 0;
                }
             }
                    
               if(Winner != 0){
                    switch (Winner) {
                        case 1:
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1+" is the Winner");
                            score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                            celebratedImg.setVisible(true);
                            cupOfwinner.setVisible(true);
                            break;
                        case 2:
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText(Game.player2+" is the Winner");
                              score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                              celebratedImg.setVisible(true);
                              cupOfwinner.setVisible(true);
                            break;
                        case 3:
                            play.setText("There is no Winner");
                            break;
                    }
                PlayAgain=true;
                 PlayButton.setDisable(false);
                }
            }}

        } catch (Exception ex) {
        }
    }

   @FXML
    private void handlePlayAction(ActionEvent event) {
      if(PlayAgain==true){
          celebratedImg.setVisible(false);
          cupOfwinner.setVisible(false);
        play.setTextFill(Color.valueOf(Game.Player1Color));
       play.setText(Game.player1+" turn");
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
        }
       
      
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(Player.player1Name, Player.player2Name,Player.player1Symbol,Player.player2Symbol);
 
        GameLogic.scoreOfPlayer1=0;
        GameLogic.scoreOfPlayer2=0;
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
