/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

/**
 *
 * @author ITI
 */
public class GameLogic {

    public String player1;
    public String player2;
    public char player1symbol;
    public char player2symbol;
    public String Player1Color;
    public String Player2Color;
    int CountToFull;
    int MatrixOfXO[][];
    public static int scoreOfPlayer1;
    public static int scoreOfPlayer2;
    int youNumber;

    {

        CountToFull = 0;
        MatrixOfXO = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                MatrixOfXO[i][j] = 0;
            }
        }
    }

    GameLogic(String player1Name, String player2Name, char Player1Pattern, char Player2Pattern) {
        player1 = player1Name;
        player2 = player2Name;

        player1symbol = Player1Pattern;
        player2symbol = Player2Pattern;
        Player1Color = "#03506f";
        Player2Color = "#fb743e";

        /*  CountToFull=0;
       MatrixOfXO = new int[3][3];
           for(int i = 0; i < 3; i++){
    for(int j = 0; j < 3; j++){
       MatrixOfXO[i][j] = 0;
    }
}*/
    }

    public GameLogic(String userName, String player1Name, String player2Name, char Player1Pattern, char Player2Pattern) {
        player1 = player1Name;
        player2 = player2Name;

        player1symbol = Player1Pattern;
        player2symbol = Player2Pattern;
        Player1Color = "#03506f";
        Player2Color = "#fb743e";
        System.out.println("+++"+userName);
        if (player1Name.equals(userName)) {
            youNumber = 1;
        } else if(player2Name.equals(userName)) {
            youNumber = 2;
        }
    }

    public int checkWinner(int PlayerNum, int index) {
        int countx = 0;
        int county = 0;
        int row = 0;
        int column = 0;
        int countDiagLeft = 0;
        int countDiagRight = 0;
        boolean isWin = false;
        while (index >= 3) {
            index -= 3;
            row++;
        }
        column = index;

        System.out.println(row + " " + column);
        MatrixOfXO[row][column] = PlayerNum;
        CountToFull++;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (MatrixOfXO[i][j] == PlayerNum) {
                    countx++;
                }
                if (MatrixOfXO[j][i] == PlayerNum) {
                    county++;
                }
                if (i == j) {
                    if (MatrixOfXO[i][j] == PlayerNum) {
                        countDiagLeft++;
                    }
                }
            }
            if (MatrixOfXO[i][2 - i] == PlayerNum) {
                countDiagRight++;
            }
            if (countx == 3 || county == 3) {
                isWin = true;
            } else {
                countx = 0;
                county = 0;
            }
        }
        if (countDiagRight == 3 || countDiagLeft == 3) {
            isWin = true;
            countDiagLeft = 0;
            countDiagRight = 0;
        }
        if (isWin == true) {
            return PlayerNum;
        } else {
            if (CountToFull == 9) {
                return 3;
            }
            return 0;
        }

    }

}
