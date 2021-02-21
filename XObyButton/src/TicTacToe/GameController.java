package TicTacToe;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

/**
 *
 * @author ITI
 */
class GameLogic {
 
    public String player1;
    public String player2; 
    public char player1_symbol;
    public char player2_symbol;
    int elem_addedTo_matrix;
    int matrix_XO[][];
    int turn=1;
    boolean check_global=false;
    char player_turn=1;
    //**Start Global Constructor 
   GameLogic(boolean is_global , String player1_name ,String player2_name ,
        char player1_symbol,char player2_symbol ){
        player1      = player1_name;
        player2      = player2_name;
        check_global = is_global;
        this.player1_symbol = player1_symbol;
        this.player2_symbol = player2_symbol;
      /*  elem_addedTo_matrix = 0;
        matrix_XO = new int[3][3];
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++)
            {
                matrix_XO[col][col] = 0;
            }
        }*/
        
   } 
   //this is The common between the 2 constructor  
   // adding Elems to matrix
   {
   elem_addedTo_matrix = 0;
        matrix_XO = new int[3][3];
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++)
            {
                matrix_XO[col][col] = 0;
            }
        }
   }
   
   GameLogic(boolean is_global , String player1_name ,String player2_name ,char Player1_pattern){
        player1      = player1_name;
        player2      = player2_name;
        check_global = is_global;

        if( 'X' == Character.toUpperCase(Player1_pattern)  ){
            player1_symbol = 'X';
            player2_symbol = 'O';
        }
        else 
        {
            player1_symbol = 'O';
            player2_symbol = 'X';
         }
        /*
        elem_addedTo_matrix = 0;
        matrix_XO = new int[3][3];
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++)
            {
                matrix_XO[col][col] = 0;
            }
        }
        */
         
  }
   
    public int checkWinner(int player_num, int index) {
        
        int count_x = 0; 
        int count_y = 0;
        int row     = 0; 
        int column  = 0;
        int count_digit_left  = 0;
        int count_digit_right = 0; 
        boolean isWin         = false;
        //convert index from [] to column value and row value in [][]
        while(index >= 3){
            index-=3;
            row++;
        }  column=index;       
        matrix_XO[row][column] = player_num;
        elem_addedTo_matrix++;
        
        for (int xo_row = 0; xo_row < 3; xo_row++) {
            for (int xo_col = 0;  xo_col < 3;  xo_col++) {
                if (matrix_XO[xo_row][ xo_col] == player_num) {
                    count_x++; 
                }
                if (matrix_XO[ xo_col][xo_row] == player_num) {
                    count_y++; 
                }
                if(xo_row== xo_col){
                    if (matrix_XO[xo_row][ xo_col] == player_num) {
                        count_digit_left++; 
                    }   
                }
            } 
            if(matrix_XO[xo_row][2-xo_row]==player_num){
                    count_digit_right++;
            }
            if (count_x == 3 || count_y == 3) {
                isWin = true;
            }
            else {
                count_x = 0;count_y = 0; 
            }
        } 
        if(count_digit_right==3||count_digit_left==3) {
            isWin =true ;
            count_digit_left = 0;
            count_digit_right = 0;
        }
        if(isWin == true){
              return player_num;
        }
        else{
            if(elem_addedTo_matrix==9){
                return 3;
            }
            return 0;
        }
    }

}
public class GameController implements Initializable {
    int X_or_O = 0;int row,column;int oneArrayIndex;int rowComp=0;int colComp=0;
    int Winner = 0;boolean PlayAgain=true; int[] intArray ;
    int player1_row=0,player1_col=0 ,player2_row=0,player2_col=0; 
    GameLogic Game ;   
    String player1_index="";
    String player2_index="";
     
    @FXML
    public Label play,player1Lb,player2Lb;
    public GridPane GridpaneForButton;
    public Button PlayButton;
    public TextField player1_tx;
    public TextField player2_tx;
    public Button player1_btn;
    public Button player2_btn;
    public TextArea sever_tx;
    //
    @FXML
    private void handlePlayer1Send(ActionEvent event)
    {
       try{
           player1_index=player1_tx.getText();
           sever_tx.appendText( player1_tx.getText()+ "\n");
           player2Lb.setText("player2: "+player1_index);
           player1_tx.setText("");
           
       }
       catch (Exception ex) {
        }
 
    }   
    //function Stop playing for player who does not have turn 
     public void pausePlaying(){
        if(Game.turn!=0){
            PlayButton.setDisable(true);
            GridpaneForButton.getChildren().forEach((node) -> {
                 ((Button)node).setText("");
            });
        }
        
   }
     
  
   //function send if player win ,loose or tie
     public void sendWinner(){
        String who_win;
        switch (Winner) {
            case 1:
                who_win="=win\n";
                break;
            case 0:
                who_win="=loose\n";
                break;
            default:
                who_win="=Tie\n";
                break;
        }
        sever_tx.appendText(who_win);
     }
     //send player number and move to server 
     public void sendMove( int move){
         sever_tx.appendText(""+move );
     }
    
     @FXML
    private void handlePlayer2Send(ActionEvent event)
    {
       try{
           player2_index+=player2_tx.getText();
           System.out.println(player2_index);
           sever_tx.appendText( player2_tx.getText()+ "\n");
           player1Lb.setText("player1: "+player2_index);
           player2_tx.setText(""); 
       }
       catch (Exception ex) {
        }
    }   
    //
    @FXML
    private void handleButtonAction(ActionEvent event) {
       try {
            if ( PlayAgain==false) {
                if( Winner == 0 )
                 
                { 
                    //to Know where the user clicked 
                    Button SelectedButton = (Button) event.getSource();
                    row = GridPane.getRowIndex(SelectedButton);
                    column = GridPane.getColumnIndex(SelectedButton);
                    oneArrayIndex = (row)*3 + column;        
                  if ("".equals(SelectedButton.getText())) {
                      //Global Gmae 
                       if(Game.check_global==true){
                         if (X_or_O==0) { 
                           sendMove(oneArrayIndex);
                           Winner=Game.checkWinner(1,oneArrayIndex);
                           //get the Data from server 
                           //>number of index and we will split the message by (" > ")
                           System.out.print(sever_tx);
                           
                           
                            //SelectedButton.setText(Character.toString(Game.player1_symbol));  
                               while( oneArrayIndex>=3)
                                {
                                       oneArrayIndex-=3;
                                       player2_row++;
                                 }  player2_col=oneArrayIndex;
                                
                           GridpaneForButton.getChildren().forEach((node) -> {
                           if(GridPane.getColumnIndex(node)==player2_col && GridPane.getRowIndex(node)==player2_row){
                                 ((Button)node).setText(Character.toString(Game.player1_symbol));
                           }
                            });                        
                             sendWinner();
                            //  Winner=Game.checkWinner(1,oneArrayIndex);
                               System.out.println(oneArrayIndex+"///"+Winner);
                               System.out.println(Winner);
                            play.setText(Game.player2+" turn"); 
                           // pausePlaying();
                            Game.turn=2;
                            X_or_O = 1;
                            player2_col=0; player2_row=0;
                         }    
                         else if ( X_or_O==1 ) { 
                              Winner=Game.checkWinner(2,oneArrayIndex);
                           sendMove(oneArrayIndex);
                           
                            //SelectedButton.setText(Character.toString(Game.player1_symbol));  
                               while( oneArrayIndex>=3)
                                {
                                       oneArrayIndex-=3;
                                       player1_row++;
                                 }  player1_col=oneArrayIndex;
                                
                           GridpaneForButton.getChildren().forEach((node) -> {
                           if(GridPane.getColumnIndex(node)==player1_col && GridPane.getRowIndex(node)==player1_row){
                                 ((Button)node).setText(Character.toString(Game.player2_symbol));
                           }
                            });                        
                             sendWinner();
                            // Winner=Game.checkWinner(2,oneArrayIndex);
                              
                            play.setText(Game.player1+" turn"); 
                           // pausePlaying();
                            Game.turn=1;
                            X_or_O = 0; 
                             player1_row=0;player1_col=0;
                         }  
       
                     }
                    
                     else if(Game.check_global==false){
                       //game friend with friend
                        if(Game.player2!="computer"){
                             if (X_or_O == 0) { 
                                SelectedButton.setText(Character.toString(Game.player1_symbol));  
                                Winner=Game.checkWinner(1,oneArrayIndex);
                                System.out.println(Winner);
                                play.setText(Game.player2+" turn"); 
                                X_or_O = 1;
                            }           
                             else if (X_or_O == 1) {
                                SelectedButton.setText(Character.toString(Game.player2_symbol));  
                                Winner=Game.checkWinner(2,oneArrayIndex);
                                System.out.println(Winner);
                                play.setText(Game.player1+" turn"); 
                                X_or_O = 0;
                            }
                        }
                        else{
                            //playing with pc
                           SelectedButton.setText(Character.toString(Game.player1_symbol));  
                            Winner=Game.checkWinner(1, oneArrayIndex);
                            System.out.println(oneArrayIndex+"///"+Winner);
                            if(Winner == 0){
                            play.setText("Computer turn"); 
                            Random r =new Random();
                          // Create another array of size one less 
                            int[] anotherArray = new int[intArray.length - 1]; 
                            for (int i = 0, k = 0; i < intArray.length; i++) { 
                                 if (intArray[i] != oneArrayIndex) { 
                                 anotherArray[k++] = intArray[i];
                                } 
                            }
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
                          ((Button)node).setText(Character.toString(Game.player2_symbol));
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
                 play.setText(Game.player1+" turn");   
                 }
                            }
                    
                        }
                      
                      }       
                ///////////////player1/////////////////
       
               if(Winner != 0){
                    switch (Winner) {
                        case 1:
                            play.setText(Game.player1+" is the Winner");
                            break;
                        case 2:
                            play.setText(Game.player2+" is the Winner");
                            break;
                        case 3:
                            play.setText("There is no winner");
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
        play.setText(Game.player1+" turn");
        intArray = new int[]{ 0,1,2,3,4,5,6,7,8 };
        X_or_O = 0;
        PlayAgain=false;
        PlayButton.setDisable(true);
        GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
        });
       Game = new GameLogic(true,"Phoebe", "Aalaa",'X','O');
        
        }
     if(Game.check_global==true){
          Game.turn=1;
          play.setText(Game.player1+" turn");
        intArray = new int[]{ 0,1,2,3,4,5,6,7,8 };
        X_or_O = 0;
        PlayAgain=false;
        PlayButton.setDisable(true);
        GridpaneForButton.getChildren().forEach((node) -> {
             ((Button)node).setText("");
        });
      }
      
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(true,"Phoebe", "Aalaa",'X','O');
        player1Lb.setText("player1: "+Game.player1);
        player2Lb.setText("player2: "+Game.player2);
        if(Game.check_global==false){
          player1Lb.setText("player1: "+Game.player1);
          player2Lb.setText("player2: "+Game.player2);
        }
      
      
    }


}


/**if (isWin == true) {
            if (pattern == 'X') {
                play.setText("Player 1 is the Winner");
            } else {
                play.setText("Player 2 is the Winner");
            }
            
           
        }**/