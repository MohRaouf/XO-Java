/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xobybutton;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

/**
 *
 * @author ITI
 */
public class FXMLDocumentController implements Initializable {

    int X_or_O = 0;
    boolean isWin = false;
    char MatrixOfXO[][];
    @FXML
    private Label play;
    // public  Button Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9 ;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if (isWin == false) {
                Button SelectedButton = (Button) event.getSource();

                if (X_or_O == 0) {
                    play.setText("Player 1 to move");
                    if (SelectedButton.getText() == "") {
                        System.out.println();
                        SelectedButton.setText("X");
                    }
                    checkWinner('X', Integer.parseInt(SelectedButton.getId()));
                    X_or_O = 1;
                } else if (X_or_O == 1) {
                    play.setText("Player 2 to move");
                    if (SelectedButton.getText() == "") {
                        SelectedButton.setText("O");
                    }
                    checkWinner('O', Integer.parseInt(SelectedButton.getId()));
                    X_or_O = 0;
                }
            }

        } catch (Exception ex) {
        }
    }

    @FXML
    private void handlePlayAction(ActionEvent event) {
        play.setText("Player 1 to move");
    }

    public void checkWinner(char pattern, int ButtonNumber) {
        int column = 0;int row = 0;int countx = 0;int county = 0;
        int countDiagLeft=0;
        int countDiagRight=0;
        while (ButtonNumber > 2) {
            ButtonNumber -= 3;
            row++; }
        column = ButtonNumber;
        MatrixOfXO[row][column] = pattern;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (MatrixOfXO[i][j] == pattern) {
                    countx++; }
                if (MatrixOfXO[j][i] == pattern) {
                    county++; }
                if(i==j){
                 if (MatrixOfXO[i][j] == pattern) {
                    countDiagLeft++;
                     System.out.println("Left"+countDiagLeft); }    }
            } if(MatrixOfXO[i][2-i]==pattern){
                    countDiagRight++;}
            if (countx == 3 || county == 3) {
                isWin = true;} else {
                countx = 0;county = 0;  }
        } 
        if(countDiagRight==3||countDiagLeft==3) isWin =true;
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
