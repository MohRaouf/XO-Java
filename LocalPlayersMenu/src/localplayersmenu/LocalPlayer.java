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
public class LocalPlayer {
    public String name;
    public String symbol;
    public  LocalPlayer(String playerName,String playerSymbol){
        name=playerName;
        symbol=playerSymbol;
    }

    LocalPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
