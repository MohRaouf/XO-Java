package xobybutton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import localplayersmenu.Player;

/**
 *
 * @author ITI
 */
class GameLogic {
 public String player1;
 public String player2; 
 public char player1symbol;
 public char player2symbol;
 public String Player1Color;
 public String Player2Color;
 int CountToFull;
 int MatrixOfXO[][];
 boolean checkGlobal;
 public static int scoreOfPlayer1;
 public static int scoreOfPlayer2;
   GameLogic(boolean isGlobal,String player1Name,String player2Name,char Player1Pattern){
      player1 = player1Name;
      player2 = player2Name;
      checkGlobal = isGlobal;
      
      if( 'X' == Character.toUpperCase(Player1Pattern)  ){
          player1symbol = 'X';
          player2symbol = 'O';
          Player1Color = "#03506f";
          Player2Color = "#fb743e";
          
       } else {
           player1symbol = 'O';
          player2symbol = 'X';
           Player2Color = "#03506f";
          Player1Color = "#fb743e";
        }
      CountToFull=0;
       MatrixOfXO = new int[3][3];
           for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
       MatrixOfXO[i][j] = 0;
    }
}
   }
    public int checkWinner(int PlayerNum, int index) {
        int countx = 0;int county = 0; int row=0;int column=0;
        int countDiagLeft=0;
        int countDiagRight=0; boolean isWin=false;
          while(index>=3){
            index-=3;
            row++;
        }  column=index;
        
        System.out.println(row+" "+column);
        MatrixOfXO[row][column] = PlayerNum;
        CountToFull++;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (MatrixOfXO[i][j] == PlayerNum) {
                    countx++; }
                if (MatrixOfXO[j][i] == PlayerNum) {
                    county++; }
                if(i==j){
                 if (MatrixOfXO[i][j] == PlayerNum) {
                    countDiagLeft++; }    }
            } if(MatrixOfXO[i][2-i]==PlayerNum){
                    countDiagRight++;}
            if (countx == 3 || county == 3) {
                isWin = true;} else {
                countx = 0;county = 0;  }
        } 
        if(countDiagRight==3||countDiagLeft==3) {isWin =true ; countDiagLeft=0;countDiagRight=0;}
          if(isWin == true){
              return PlayerNum;
          }else{
              if(CountToFull==9){
                  return 3;
              }
              return 0;
          }
         
        
    }

}
public class FXMLDocumentController implements Initializable {

    int X_or_O = 0;int row,column;int oneArrayIndex;int rowComp=0;int colComp=0;
    int Winner = 0;boolean PlayAgain=true; int[] intArray ;
  GameLogic Game ;
    @FXML
   public Label play,player1Lb,player2Lb,Pattern1,Pattern2,score1,score2;
   public GridPane GridpaneForButton;
   public Button PlayButton;
  
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
                ///////////////player1/////////////////
              /*  SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
       SelectedButton.setText(Character.toString(Game.player1symbol));  
                    Winner=Game.checkWinner(1, oneArrayIndex);
                      System.out.println(oneArrayIndex+"///"+Winner);
                      if(Winner == 0){
                      /*    play.setTextFill(Color.valueOf(Game.Player2Color));
                    play.setText("Computer turn"); */
                    //  Random r =new Random();
                          // Create another array of size one less 
                  /*    int[] anotherArray = new int[intArray.length - 1]; 
                      for (int i = 0, k = 0; i < intArray.length; i++) { 
                         if (intArray[i] != oneArrayIndex) { 
                             anotherArray[k++] = intArray[i];
                            }   }
                          intArray = anotherArray;
                      int randomIndex = r.nextInt(intArray.length);
                      
                         int Index=intArray[randomIndex];
                         System.out.println(Index);
                          while( Index>=3){
                        Index-=3;
                         rowComp++;
                      }  colComp=Index;
                    System.out.println(rowComp+"--"+colComp);
                      GridpaneForButton.getChildren().forEach((node) -> {
                           if(GridPane.getColumnIndex(node)==colComp && GridPane.getRowIndex(node)==rowComp){
                               ((Button)node).setTextFill(Color.valueOf(Game.Player2Color));
                          ((Button)node).setText(Character.toString(Game.player2symbol));
                         System.out.println(rowComp+"**"+colComp);
                          }
            });
               Winner=Game.checkWinner(2, intArray[randomIndex]);
            rowComp=0;colComp=0;
                anotherArray = new int[intArray.length - 1]; 
                      for (int i = 0, k = 0; i < intArray.length; i++) { 
                         if (intArray[i] != intArray[randomIndex]) { 
                             anotherArray[k++] = intArray[i];
                            }   }
                          intArray = anotherArray;
                  play.setTextFill(Color.valueOf(Game.Player1Color));
                 play.setText(Game.player1+" turn");   
                 }*/}
                    
               if(Winner != 0){
                    switch (Winner) {
                        case 1:
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1+" is the Winner");
                           
                            score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
     
                            break;
                        case 2:
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText(Game.player2+" is the Winner");
                              score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
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
        Winner=0;
        play.setTextFill(Color.valueOf(Game.Player1Color));
      play.setText(Game.player1+" turn");
      intArray = new int[]{ 0,1,2,3,4,5,6,7,8 };
       X_or_O = 0;
        PlayAgain=false;
         PlayButton.setDisable(true);
         GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
            });
         Game = new GameLogic(false,"phoebe", "Pola",'X');
        }
       
      
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(false,Player.player1Name, Player.player2Name,Player.player1Symbol);
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
/**if (isWin == true) {
            if (pattern == 'X') {
                play.setText("Player 1 is the Winner");
            } else {
                play.setText("Player 2 is the Winner");
            }
            
           
        }**/