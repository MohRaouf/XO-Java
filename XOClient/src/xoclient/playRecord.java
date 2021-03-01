package xoclient;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ITI
 */
public class playRecord extends Thread {

    public GameFriendController friendController;
    public GameComputerController computerController;
    public HardLevelController hardController;
    String[] LineSplitted;
    int number;
    int Index;
    int whichController = 0;
    int rowComp = 0;
    int colComp = 0;
    FXMLLoader loader;

    public playRecord(String Line, GameFriendController _friendController) {
        friendController = _friendController;
        LineSplitted = Line.split(",");
        number = 1;
        whichController = 1;
    }

    public playRecord(String Line, GameComputerController _ComputerController) {
        computerController = _ComputerController;
        LineSplitted = Line.split(",");
        number = 1;
        whichController = 2;

    }

    public playRecord(String Line, HardLevelController _HardController) {
        hardController = _HardController;
        LineSplitted = Line.split(",");
        number = 1;
        whichController = 3;

    }

    public void run() {

        Platform.runLater(() -> {
            switch (whichController) {
                case 1:
                    friendController.initScreen(LineSplitted[1], LineSplitted[2], LineSplitted[3].charAt(0), LineSplitted[4].charAt(0));
                    friendController.GridpaneForButton.getChildren().forEach((node) -> {
                        ((Button) node).setText("");
                    });
                    break;
                case 2:
                    computerController.initScreen(LineSplitted[1], LineSplitted[2], LineSplitted[3].charAt(0), LineSplitted[4].charAt(0));
                    computerController.GridpaneForButton.getChildren().forEach((node) -> {
                        ((Button) node).setText("");
                    });
                    break;
                case 3:
                    hardController.initScreen(LineSplitted[1], LineSplitted[2], LineSplitted[3].charAt(0), LineSplitted[4].charAt(0));
                    hardController.GridpaneForButton.getChildren().forEach((node) -> {
                        ((Button) node).setText("");
                    });
                    break;
            }
        });

        for (int i = 5; i < LineSplitted.length; i++) {
            rowComp = 0;
            colComp = 0;
            int Index = Integer.parseInt(LineSplitted[i]);
            if (Index < 3) {
                rowComp = 0;
                colComp = Index;
            } else {
                while (Index >= 3) {
                    Index -= 3;
                    rowComp++;
                    colComp = Index;
                }
            }

            System.out.println(rowComp + "--" + colComp);
            Platform.runLater(() -> {

                if (number == 1) {
                    switch (whichController) {
                        case 1:
                            friendController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {

                                    ((Button) node).setTextFill(Color.valueOf(friendController.Game.Player1Color));
                                    ((Button) node).setText(Character.toString(friendController.Game.player1symbol));
                                    friendController.play.setTextFill(Color.valueOf(friendController.Game.Player2Color));
                                    friendController.play.setText(friendController.Game.player2 + " turn");
                                }
                            });
                            break;
                        case 2:
                            computerController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {

                                    ((Button) node).setTextFill(Color.valueOf(computerController.Game.Player1Color));
                                    ((Button) node).setText(Character.toString(computerController.Game.player1symbol));
                                    computerController.play.setTextFill(Color.valueOf(computerController.Game.Player2Color));
                                    computerController.play.setText(computerController.Game.player2 + " turn");
                                }
                            });
                            break;
                        case 3:
                            hardController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {

                                    ((Button) node).setTextFill(Color.valueOf(hardController.Game.Player1Color));
                                    ((Button) node).setText(Character.toString(hardController.Game.player1symbol));
                                    hardController.play.setTextFill(Color.valueOf(hardController.Game.Player2Color));
                                    hardController.play.setText(hardController.Game.player2 + " turn");
                                }
                            });
                            break;
                    }

                    number = 2;
                } else {
                    switch (whichController) {
                        case 1:
                            friendController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {

                                    ((Button) node).setTextFill(Color.valueOf(friendController.Game.Player2Color));
                                    ((Button) node).setText(Character.toString(friendController.Game.player2symbol));
                                    friendController.play.setTextFill(Color.valueOf(friendController.Game.Player1Color));
                                    friendController.play.setText(friendController.Game.player1 + " turn");
                                }
                            });
                            break;
                        case 2:
                            computerController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {
                                    ((Button) node).setTextFill(Color.valueOf(computerController.Game.Player2Color));
                                    ((Button) node).setText(Character.toString(computerController.Game.player2symbol));
                                    computerController.play.setTextFill(Color.valueOf(computerController.Game.Player1Color));
                                    computerController.play.setText(computerController.Game.player1 + " turn");
                                }
                            });
                            break;
                        case 3:
                            hardController.GridpaneForButton.getChildren().forEach((node) -> {
                                if (GridPane.getColumnIndex(node) == colComp && GridPane.getRowIndex(node) == rowComp) {
                                    ((Button) node).setTextFill(Color.valueOf(hardController.Game.Player2Color));
                                    ((Button) node).setText(Character.toString(hardController.Game.player2symbol));
                                    hardController.play.setTextFill(Color.valueOf(hardController.Game.Player1Color));
                                    hardController.play.setText(hardController.Game.player1 + " turn");
                                }
                            });
                            break;
                    }
                    number = 1;

                }

                System.out.println(rowComp + "**" + colComp);

            });

            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(playRecord.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
