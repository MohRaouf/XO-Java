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
    @FXML
    private Label play;
    public  Button Button1,Button2,Button3,Button4,Button5,Button6,Button7,Button8,Button9 ;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if(isWin==false){
            Button SelectedButton = (Button) event.getSource();
          
            if(X_or_O==0){
                play.setText("Player 1 to move");
                if(SelectedButton.getText()==""){
            SelectedButton.setText("X");}
            checkWinner("X");
            X_or_O=1;
            }else if(X_or_O==1){
             play.setText("Player 2 to move");
             if(SelectedButton.getText()==""){
            SelectedButton.setText("O");}
            checkWinner("O");
            X_or_O=0;
        }}
            
        } catch (Exception ex) {
        }
    }
    @FXML 
    private void handlePlayAction(ActionEvent event){
         play.setText("Player 1 to move");
    }
     public void checkWinner(String pattern){
         if(Button1.getText()== pattern && Button2.getText()== pattern && Button3.getText()==pattern){
            isWin=true;
         }
         if(Button1.getText()== pattern && Button4.getText()== pattern && Button7.getText()==pattern){
            isWin=true;
         }
         if(Button1.getText()== pattern && Button5.getText()== pattern && Button9.getText()==pattern){
            isWin=true;
         }
         if(Button2.getText()== pattern && Button5.getText()== pattern && Button8.getText()==pattern){
            isWin=true;
         }
         if(Button4.getText()== pattern && Button5.getText()== pattern && Button6.getText()==pattern){
            isWin=true;
         }
         if(Button7.getText()== pattern && Button8.getText()== pattern && Button9.getText()==pattern){
            isWin=true;
         }
         if(Button3.getText()== pattern && Button6.getText()== pattern && Button9.getText()==pattern){
            isWin=true;
         }
         if(Button3.getText()== pattern && Button5.getText()== pattern && Button7.getText()==pattern){
            isWin=true;
         }
         if(isWin==true){
             System.out.println("yes");
             if(pattern=="X")
                 play.setText("Player 1 is the Winner");
             else
                 play.setText("Player 2 is the Winner");
         }
     }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
  
    }

}
