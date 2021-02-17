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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author ITI
 */
public class FXMLDocumentController implements Initializable {

    int X_or_O = 0;int row,column;
    boolean isWin = false;boolean ReadyToplay=false;
    char MatrixOfXO[][];
    @FXML
    private Label play;
   public GridPane GridpaneForButton;
   public Button PlayButton;
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if ( ReadyToplay==true) {
                if(isWin == false ){
                Button SelectedButton = (Button) event.getSource();

                if (X_or_O == 0) {
                    play.setText("Player 2 to move");
                    if ("".equals(SelectedButton.getText())) {
                        SelectedButton.setText("X");
                          row = GridPane.getRowIndex(SelectedButton);
                         column = GridPane.getColumnIndex(SelectedButton);
                         System.out.println(row+"+"+column);
                    }
                    checkWinner('X', row,column);
                    X_or_O = 1;
                } else if (X_or_O == 1) {
                    play.setText("Player 1 to move");
                    if ("".equals(SelectedButton.getText())) {
                        SelectedButton.setText("O");
                         row = GridPane.getRowIndex(SelectedButton);
                         column = GridPane.getColumnIndex(SelectedButton);
                         System.out.println(row+"+"+column);
                    }
                    checkWinner('O',row,column );
                    X_or_O = 0;
                }
                
            }else{
                 ReadyToplay=false;
                 PlayButton.setDisable(false);
            }}

        } catch (Exception ex) {
        }
    }

   @FXML
    private void handlePlayAction(ActionEvent event) {
        if(ReadyToplay==false){
        isWin=false;
        play.setText("Player 1 to move");
        
        ReadyToplay=true;
         PlayButton.setDisable(ReadyToplay);
          
         GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
            });
     for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
       MatrixOfXO[i][j] = 0;
    }
}
        }
        
      
    }

    public void checkWinner(char pattern, int row,int column) {
        int countx = 0;int county = 0;
        int countDiagLeft=0;
        int countDiagRight=0;
        MatrixOfXO[row][column] = pattern;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (MatrixOfXO[i][j] == pattern) {
                    countx++; }
                if (MatrixOfXO[j][i] == pattern) {
                    county++; }
                if(i==j){
                 if (MatrixOfXO[i][j] == pattern) {
                    countDiagLeft++; }    }
            } if(MatrixOfXO[i][2-i]==pattern){
                    countDiagRight++;}
            if (countx == 3 || county == 3) {
                isWin = true;} else {
                countx = 0;county = 0;  }
        } 
        if(countDiagRight==3||countDiagLeft==3) {isWin =true ; countDiagLeft=0;countDiagRight=0;}
        if (isWin == true) {
            if (pattern == 'X') {
                play.setText("Player 1 is the Winner");
            } else {
                play.setText("Player 2 is the Winner");
            }
            
           
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MatrixOfXO = new char[3][3];

    }


}
