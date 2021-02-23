/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localplayersmenu;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.net.*;
import java.io.*;
/**
 *
 * @author ITI
 */
public class FXMLGlobalController extends Thread implements Initializable{

    int X_or_O = 0;int row,column;int index,oneArrayIndex;int rowComp=0;int colComp=0;
    int Winner = 0;boolean PlayAgain=false; int[] intArray ; String  Received;
    char yourSymbol;
    char anotherSymbol;
    int yourNumber;
    String yourColor;
    String anotherColor;
    Socket mySocket;
    DataInputStream Datais;
    PrintStream ps;
  GameLogic Game ;
    @FXML
   public Label play,player1Lb,player2Lb,Pattern1,Pattern2,score1,score2;
   public GridPane GridpaneForButton;
   public Button PlayButton;
   public ImageView celebratedImg,cupOfwinner,LoseImage;
   public AnchorPane mainPane;
    public TextArea server_tx;
    @FXML
    private void handleButtonAction(ActionEvent event) {
       try {
          
            if ( PlayAgain==false) {
                 if(Received != ""){
                     
                if( Winner == 0 ){
                   
                Button SelectedButton = (Button) event.getSource();
                       row = GridPane.getRowIndex(SelectedButton);
                         column = GridPane.getColumnIndex(SelectedButton);
                          oneArrayIndex = (row)*3 + column;
                         
                  if ("".equals(SelectedButton.getText())) {           
                         SelectedButton.setTextFill(Color.valueOf(yourColor));
                        SelectedButton.setText(Character.toString(yourSymbol));  
                    Winner=Game.checkWinner(1,oneArrayIndex);
                    System.out.println(Winner);
                      play.setTextFill(Color.valueOf(anotherColor));
                    play.setText(anotherColor+" turn"); 
                     ps.println(oneArrayIndex+","+Winner);
                     Received="";
                  }
                    
               if(Winner != 0){
                    switch (Winner) {
                        case 1:
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1+" is the Winner");
                            score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                            if(yourNumber==Winner){
                            celebratedImg.setVisible(true);
                            cupOfwinner.setVisible(true);}
                            else LoseImage.setVisible(true);
                            break;
                        case 2:
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText(Game.player2+" is the Winner");
                              score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                              if(yourNumber==Winner){
                              celebratedImg.setVisible(true);
                              cupOfwinner.setVisible(true);}
                              else LoseImage.setVisible(true);
                            break;
                        case 3:
                            play.setText("There is no Winner");
                            break;
                    }
                PlayAgain=true;
                 PlayButton.setDisable(false);
                }
            }}
            }

        } catch (Exception ex) {
        }
    }

   @FXML
    private void handlePlayAction(ActionEvent event) {
      if(PlayAgain==true){
          celebratedImg.setVisible(false);
          cupOfwinner.setVisible(false);
          LoseImage.setVisible(false);
      play.setTextFill(Color.valueOf(Game.Player1Color));
      play.setText(Game.player1+" turn");
    
        PlayAgain=false;
         Winner=0;
         PlayButton.setDisable(true);
         GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
            });
         Game = new GameLogic(Player.player1Name, Player.player2Name,Player.player1Symbol,true);
        }
       
      
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
             try{
             mySocket = new Socket(InetAddress.getLocalHost(),4800);
              Datais = new DataInputStream(mySocket.getInputStream());
              ps = new PrintStream(mySocket.getOutputStream());}catch(IOException ex){
             ex.printStackTrace();
         } start();
        Game = new GameLogic(Player.player1Name, Player.player2Name,Player.player1Symbol,true);
        if(Game.youWillStart==true){
            yourSymbol= Game.player1symbol;
            yourColor = Game.Player1Color;
            anotherSymbol=Game.player2symbol;
            anotherColor=Game.Player2Color;
            yourNumber=1;
        } else{
            yourSymbol= Game.player2symbol;
            yourColor = Game.Player2Color;
            anotherSymbol=Game.player1symbol;
            anotherColor=Game.Player1Color;
            yourNumber=2;
                    }
        
        GameLogic.scoreOfPlayer1=0;
        GameLogic.scoreOfPlayer2=0;
        PlayButton.setDisable(true);
        play.setTextFill(Color.valueOf(Game.Player1Color));
       play.setText(Game.player1+" turn");
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

    @Override
  public void run(){
      try{
       while(true){
       
           Received = Datais.readUTF();
            String[] words=Received.split(","); //-1,0  
                      Winner = Integer.parseInt(words[1]);
                      index=Integer.parseInt(words[0]);
                      if(index != -1){
                       while( index>=3){
                        index-=3;
                         rowComp++;
                      }  colComp=index;
                    System.out.println(rowComp+"--"+colComp);
                      GridpaneForButton.getChildren().forEach((node) -> {
                           if(GridPane.getColumnIndex(node)==colComp && GridPane.getRowIndex(node)==rowComp){
                               ((Button)node).setTextFill(Color.valueOf(anotherColor));
                          ((Button)node).setText(Character.toString(anotherSymbol));
                         System.out.println(rowComp+"**"+colComp);
                          }
            });}
       }}catch(Exception ex){
            ex.printStackTrace();
        }
 }
}
