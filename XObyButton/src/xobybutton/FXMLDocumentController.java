/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xobybutton;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ITI
 */
class GameLogic {
 public String player1;
 public String player2; 
 public int Pattern;
 int CountToFull;
 int MatrixOfXO[][];
   GameLogic(String player1Name,String player2Name){
      player1 = player1Name;
      player2 = player2Name;
      CountToFull=0;
       MatrixOfXO = new int[3][3];
           for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
       MatrixOfXO[i][j] = 0;
    }
}
   }
    public int checkWinner(int PlayerNum, int row,int column) {
        Pattern=PlayerNum;
        int countx = 0;int county = 0; 
        int countDiagLeft=0;
        int countDiagRight=0; boolean isWin=false;
        MatrixOfXO[row][column] = Pattern;
        CountToFull++;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (MatrixOfXO[i][j] == Pattern) {
                    countx++; }
                if (MatrixOfXO[j][i] == Pattern) {
                    county++; }
                if(i==j){
                 if (MatrixOfXO[i][j] == Pattern) {
                    countDiagLeft++; }    }
            } if(MatrixOfXO[i][2-i]==Pattern){
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

    int X_or_O = 0;int row,column;
    int Winner = 0;boolean ReadyToplay=false; 
   GameLogic Game = new GameLogic("phoebe", "pola");
    @FXML
   public Label play,player1,player2;
   public GridPane GridpaneForButton;
   public Button PlayButton;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if ( ReadyToplay==true) {
                if( Winner == 0 ){
                Button SelectedButton = (Button) event.getSource();
                       row = GridPane.getRowIndex(SelectedButton);
                         column = GridPane.getColumnIndex(SelectedButton);
                  if ("".equals(SelectedButton.getText())) {
                if (X_or_O == 0) {
                    play.setText("Player 2 to move");  
                        SelectedButton.setText("X");  
                    Winner=Game.checkWinner(1, row,column);
                    X_or_O = 1;
                } else if (X_or_O == 1) {
                    play.setText("Player 1 to move");
                        SelectedButton.setText("O");
                     Winner=Game.checkWinner(2,row,column );
                    X_or_O = 0;
                }}
                    System.out.println(Winner);
                if(Winner != 0){
                    switch (Winner) {
                        case 1:
                            play.setText("Player 1 is the Winner");
                            break;
                        case 2:
                            play.setText("Player 2 is the Winner");
                            break;
                        case 3:
                            play.setText("There is no winner");
                            break;
                    }
                ReadyToplay=false;
                 PlayButton.setDisable(false);
                }
            }}

        } catch (Exception ex) {
        }
    }

   @FXML
    private void handlePlayAction(ActionEvent event) {
        if(ReadyToplay==false){
        Winner=0;
      play.setText("Player 1 to move");
       X_or_O = 0;
        ReadyToplay=true;
         PlayButton.setDisable(ReadyToplay);
         GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
            });
         Game = new GameLogic("phoebe", "pola");
        }
        
      
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      player1.setText("player1:"+Game.player1);
      player2.setText("player2:"+Game.player2);
      
    }


}
/**if (isWin == true) {
            if (pattern == 'X') {
                play.setText("Player 1 is the Winner");
            } else {
                play.setText("Player 2 is the Winner");
            }
            
           
        }**/