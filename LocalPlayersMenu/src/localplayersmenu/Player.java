/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localplayersmenu;

/**
 *
 * @author mohamedbassiouny
 */
public class Player {
    public static  String player1Name;
    public static char player1Symbol;
    public static String player2Name;
    public static char player2Symbol;
    public Player(String name1,char symbol1,String name2,char symbol2){
        Player.player1Name=name1;
        Player.player2Name=name2;
        Player.player1Symbol=symbol1;
        Player.player2Symbol=symbol2;
    }
}
