/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
//import localplayersmenu.Player;

/**
 *
 * @author ITI
 */
public class GameComputerController implements Initializable {

    String player1Name;
    String player2Name;
    char player1Pattern;
    char player2Pattern;
    Stage primaryStage;
    String RecordLine;
    int X_or_O = 0;
    int row, column;
    int oneArrayIndex;
    int rowComp = 0;
    int colComp = 0;
    int Winner = 0;
    boolean PlayAgain = true;
    boolean isRecorded = false;
    FileWriter fileWriter = null;
    PrintWriter printWriter = null;
    File recordFile;
    int[] intArray;
    GameLogic Game;
    List<Integer> RecordSteps;
    @FXML
    public Label play, player1Lb, player2Lb, Pattern1, Pattern2, score1, score2;
    public GridPane GridpaneForButton;
    public Button PlayButton,recordButton, backButton;
    public ImageView celebratedImg, cupOfwinner;
    public AnchorPane mainPane;

    public GameComputerController(Stage _primaryStage, String player1Name, char player1Pattern, String player2Name, char player2Pattern,boolean recordIt) throws IOException {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Pattern = player1Pattern;
        this.player2Pattern = player2Pattern;
        this.primaryStage = _primaryStage;
        this.isRecorded = recordIt;
            if (isRecorded == true) {
            RecordLine = "";
            StartRecording(player1Name, player2Name, Character.toString(player1Pattern), Character.toString(player2Pattern));
            recordFile = new File("RecordFile.txt");
            if (recordFile.createNewFile()) {
                System.out.println("File is created!");
            } else {
                System.out.println("File already exists.");
            }
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        try {
            if (PlayAgain == false) {
                if (Winner == 0) {

                    Button SelectedButton = (Button) event.getSource();
                    row = GridPane.getRowIndex(SelectedButton);
                    column = GridPane.getColumnIndex(SelectedButton);
                    oneArrayIndex = (row) * 3 + column;

                    if ("".equals(SelectedButton.getText())) {
                        if (isRecorded == true) {
                                RecordSteps.add(oneArrayIndex);
                            }
                        SelectedButton.setTextFill(Color.valueOf(Game.Player1Color));
                        SelectedButton.setText(Character.toString(Game.player1symbol));
                        Winner = Game.checkWinner(1, oneArrayIndex);
                        System.out.println(oneArrayIndex + "///" + Winner);
                        if (Winner == 0) {
                            play.setTextFill(Color.valueOf(Game.Player2Color));
                            play.setText("Computer turn");
                            Random r = new Random();
                            // Create another array of size one less 
                            int[] anotherArray = new int[intArray.length - 1];
                            for (int i = 0, k = 0; i < intArray.length; i++) {
                                if (intArray[i] != oneArrayIndex) {
                                    anotherArray[k++] = intArray[i];
                                }
                            }
                            intArray = anotherArray;
                            int randomIndex = r.nextInt(intArray.length);

                            int Index = intArray[randomIndex];
                            if (isRecorded == true) {
                                RecordSteps.add(Index);
                            }
                            System.out.println(Index);
                            while (Index >= 3) {
                                Index -= 3;
                                rowComp++;
                            }
                            colComp = Index;
                            System.out.println(rowComp + "--" + colComp);
                            GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {
                                    ((Button) node).setTextFill(Color.valueOf(Game.Player2Color));
                                    ((Button) node).setText(Character.toString(Game.player2symbol));
                                    System.out.println(rowComp + "**" + colComp);
                                }
                            });
                            Winner = Game.checkWinner(2, intArray[randomIndex]);
                            rowComp = 0;
                            colComp = 0;
                            anotherArray = new int[intArray.length - 1];
                            for (int i = 0, k = 0; i < intArray.length; i++) {
                                if (intArray[i] != intArray[randomIndex]) {
                                    anotherArray[k++] = intArray[i];
                                }
                            }
                            intArray = anotherArray;
                            play.setTextFill(Color.valueOf(Game.Player1Color));
                            play.setText(Game.player1 + " turn");
                        }
                    }

                    WinnerAction();
                }
            }

        } catch (Exception ex) {
        }
    }

    @FXML
    private void handlePlayAction(ActionEvent event) {
        if (PlayAgain == true) {
            if(isRecorded)
            {
                RecordLine="";
                StartRecording(player1Name, player2Name, Character.toString(player1Pattern), Character.toString(player2Pattern));
            }
            backButton.setDisable(true);
            celebratedImg.setVisible(false);
            cupOfwinner.setVisible(false);
            play.setTextFill(Color.valueOf(Game.Player1Color));
            play.setText(Game.player1 + " turn");
            intArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
            PlayAgain = false;
            Winner = 0;
            PlayButton.setDisable(true);
            GridpaneForButton.getChildren().forEach((node) -> {
                ((Button) node).setText("");
            });
            Game = new GameLogic(player1Name, player2Name, player1Pattern, player2Pattern);
        }
initScreen(Game.player1, Game.player2, Game.player1symbol, Game.player2symbol);
    }

    @FXML
    private void back(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SinglePlayer.fxml"));
        SinglePlayerController singlePlayerController = new SinglePlayerController(primaryStage);
        loader.setController(singlePlayerController);
        primaryStage.setTitle("XO Dashboard");
        Scene scene = new Scene((Parent) loader.load());
        primaryStage.setScene(scene);
    }
       @FXML
    private void RecordAction(ActionEvent event) throws IOException {
         BufferedReader reader;
        List<String> choices=new ArrayList<>();
        List<String> Lines = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader("RecordFile.txt"));
            String line = reader.readLine();
            String[] LineSplitted;
            while (line != null) {
                //System.out.println(line);
                LineSplitted = line.split(",");
                choices.add(LineSplitted[0]);
                Lines.add(line);
                //  comboBox.getItems().add(LineSplitted[0]);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
        }
         System.out.println(choices);
        ChoiceDialog<String> dialog = new ChoiceDialog(choices.get(0),choices);
               dialog.setTitle("choose from Records");

        java.util.Optional<String> result = dialog.showAndWait();
        System.out.println(choices.indexOf(dialog.getSelectedItem())); 
         if(result.isPresent()) 
      {
           int LineIndex = choices.indexOf(dialog.getSelectedItem());
        //String str = "2021-02-28 00:18:53.465,feby,pola,o,x,4,0,3,1,5";
        playRecord Play = new playRecord(Lines.get(LineIndex), this);
       
         Play.start();
      }else{
          
      }
       
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Game = new GameLogic(player1Name, player2Name, player1Pattern, player2Pattern);

        GameLogic.scoreOfPlayer1 = 0;
        GameLogic.scoreOfPlayer2 = 0;
         initScreen(Game.player1, Game.player2, Game.player1symbol, Game.player2symbol);

    }
    void initScreen(String Name1, String Name2, char Symbol1, char Symbol2) {
        player1Lb.setText(Name1);
        player2Lb.setText(Name2);
        Pattern1.setTextFill(Color.valueOf(Game.Player1Color));
        Pattern2.setTextFill(Color.valueOf(Game.Player2Color));
        score1.setTextFill(Color.valueOf(Game.Player1Color));
        score2.setTextFill(Color.valueOf(Game.Player2Color));
        Pattern1.setText(Character.toString(Symbol1));
        Pattern2.setText(Character.toString(Symbol2));
        score1.setText(Integer.toString(GameLogic.scoreOfPlayer1));
        score2.setText(Integer.toString(GameLogic.scoreOfPlayer2));
         celebratedImg.setVisible(false);
            cupOfwinner.setVisible(false);
    }

    void WinnerAction() throws IOException {

        if (Winner != 0) {
            switch (Winner) {
                case 1:
                    play.setTextFill(Color.valueOf(Game.Player1Color));
                    play.setText(Game.player1 + " is the Winner");
                    score1.setText(Integer.toString(++GameLogic.scoreOfPlayer1));
                    celebratedImg.setVisible(true);
                    cupOfwinner.setVisible(true);
                    break;
                case 2:
                    play.setTextFill(Color.valueOf(Game.Player2Color));
                    play.setText(Game.player2 + " is the Winner");
                    score2.setText(Integer.toString(++GameLogic.scoreOfPlayer2));
                    celebratedImg.setVisible(true);
                    cupOfwinner.setVisible(true);
                    break;
                case 3:
                    play.setText("There is no Winner");
                    break;
            }
             backButton.setDisable(false);

            if (isRecorded == true) {
                for (int i : RecordSteps) {
                    RecordLine += "," + i;
                }
                System.out.println(RecordLine);
                fileWriter = new FileWriter(recordFile, true);
                BufferedWriter Buffered = new BufferedWriter(fileWriter);
                Buffered.write(RecordLine + "\n");
                //printWriter  = new PrintWriter(Buffered);
                //printWriter.println(RecordLine);
                Buffered.close();
            }
            isRecorded = false;
            PlayAgain = true;
            PlayButton.setDisable(false);
        }
    }
    void StartRecording(String Player1Name, String Player2Name, String Player1Symbol, String Player2Symbol) {
        RecordLine = "";
        RecordSteps = new ArrayList<>();
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        RecordLine = date + " " + time + "," + Player1Name + "," + Player2Name + "," + Player1Symbol + "," + Player2Symbol;

    }
}
