package xoserver;

import java.io.*;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ArrayList;
import javafx.application.Platform;
import static xoserver.FXMLDocumentController.dbConnection;

class ClientThread extends Thread {

    DataInputStream dis;
    PrintStream ps;
    String playerUsername = "Player";
    ClientThread opponentThread;
    FXMLDocumentController controls;
    boolean isAuthorized;
    boolean isReady;
    static List<ClientThread> clientsList = new ArrayList<ClientThread>();

    public ClientThread(Socket clientSocket, FXMLDocumentController _controls) {
        this.controls = _controls;
        try {
            dis = new DataInputStream(clientSocket.getInputStream());
            ps = new PrintStream(clientSocket.getOutputStream());
            clientsList.add(this);
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String clientMsg = dis.readLine();
                if (clientMsg != null) {
                    
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controls.received_data_area.appendText(playerUsername + " : " + clientMsg + "\n");
                        }
                    });
                    
                    //String with # means the authentication message
                    if (clientMsg.contains("#")) {
                        //authorize the player either login or register
                        authorize(clientMsg);
                    } else if (clientMsg.equals("get info")) {
                        //get the players info from the DB, send formatted info string
                        sendInfo();
                    } else if (clientMsg.contains("$")) {
                        //game request or accept is detected
                        requestGame(clientMsg);
                    } else if (clientMsg.contains("=")) {
                        //get the players info from the DB, send formatted info string
                        gameResultAction(clientMsg);
                    } else if (clientMsg.contains("logout")) {
                        //throw exception manually to go to catch clause to remove the Client Thread
                        throw new Exception("Player Logged Out");
                    } else {
                        //game move detected forward to the opponent
                        this.opponentThread.ps.println(clientMsg);
                    }
                }
            }
        } catch (Exception e) {
            //remove the client Thread from the list
            clientsList.remove(this);
            //close the client streams
            try {
                dis.close();
                ps.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void gameResultAction(String playerMsg) {
        try {

            PreparedStatement pst = dbConnection.prepareStatement("update players_info set score=score+? where username=?;");
            pst.setString(2, this.playerUsername);
            if (playerMsg.equals("=win")) {
                pst.setInt(1, 20);
            } else if (playerMsg.equals("=lose")) {
                pst.setInt(1, 5);
            } else {
                pst.setInt(1, -5);
            }
            pst.executeUpdate();
            opponentThread = null; //remove the opponent Thread

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void requestGame(String playerMsg) {
        //filter the message either a request with player name $player or respond to game request $yes,player
        String opponentName = (playerMsg.contains("$yes") || playerMsg.contains("$no")) ? playerMsg.split(",")[1] : playerMsg.split("\\$")[1];
        //Find the opponent Thread using stream
        Optional< ClientThread> findOpponentThread = clientsList.stream().filter(client -> client.playerUsername.equals(opponentName)).findFirst();
        ClientThread opponentThread;

        if (findOpponentThread.isPresent()) {          //if Online

            opponentThread = findOpponentThread.get(); //set reference to the opponent thread

            if (opponentThread.opponentThread == null) {   //if the opponent is not in a game

                //mark the player thread as not ready so no other players request play with him 
                if (playerMsg.contains("$yes")) {  // received message = $yes,opponentUsername      

                    String startGame = "$yes," + opponentName + ",X," + this.playerUsername + ",O"; //game info
                    opponentThread.ps.println(startGame);   //send yes and game info to both players
                    this.ps.println(startGame);

                    //assign the players Thread to both players objects
                    this.opponentThread = opponentThread;
                    opponentThread.opponentThread = this;
                    isReady = false;

                } else if (playerMsg.contains("$no")) {         // if received message = $no,username

                    opponentThread.ps.println("$no," + this.playerUsername);   //forward to the opponent
                    isReady = true;
                    opponentThread.isReady = true;

                } else {  // if received message = $opponentUsername
                    if (opponentThread.isReady) {
                        opponentThread.ps.println("$" + this.playerUsername);   //send the player name who asked to play to his opponent
                        System.out.println(this.playerUsername);
                        isReady = false;
                    }
                }
            } else {
                this.ps.println("$ingame," + opponentName);  //opponent is already in game
                isReady = true;  //mark the current playerThread as ready to be open to accept others requests
            }
        } else {
            this.ps.println("$offline," + opponentName);  //not available - Offline
            isReady = true;     //mark the current playerThread as ready to be open to accept others requests
        }

    }

    //Send the Game Info : Available - Status - Score
    public void sendInfo() {
        try {
            isReady = true;
            opponentThread = null;
            Statement stmt = dbConnection.createStatement();
            String query = "select * from players_info order by score desc ;";
            ResultSet rs = stmt.executeQuery(query);
            List<String> infoList = new ArrayList<>();
            while (rs.next()) {
                String playerInfo;
                String username = rs.getString(1);
                //check if the player of the record is online
                boolean isOnline = clientsList.stream().anyMatch(client -> client.playerUsername.equals(username));
                if (isOnline) {
                    playerInfo = username + "|" + "online" + "|" + rs.getString(3);
                } else {
                    playerInfo = username + "|" + "offline" + "|" + rs.getString(3);
                }
                infoList.add(playerInfo);
                isReady = true;
            }
            //format the final string to match @player1|status|score,player2|status|score,.....
            String info = "@" + String.join(",", infoList);
            //send the info string back to the player
            this.ps.println(info);

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Authorize the client either by login or new registraion
    public void authorize(String playerMsg) {
        String username = playerMsg.split(",")[1];
        String password = playerMsg.split(",")[2];

        boolean isAuthenticated = clientsList.stream().anyMatch(client -> client.playerUsername.equals(username) && client.isAuthorized);
        if (isAuthenticated) {
            this.ps.println("#already");
        } else {

            if (playerMsg.contains("#login")) {
                //Authenticate user
                if (authenticate(username, password)) {
                    this.playerUsername = username;
                    isAuthorized = true;
                    //Confirm
                    this.ps.println("#done");
                } else {
                    this.ps.println("#no");
                }
            } else if (playerMsg.contains("#register")) {
                //register user
                if (register(username, password)) {
                    this.playerUsername = username;
                    isAuthorized = true;
                    //Confirm
                    this.ps.println("#done");
                } else {
                    this.ps.println("#no");
                }
            }
        }
    }

    //Authorize with login
    public boolean authenticate(String username, String password) {
        try {
            Statement stmt = dbConnection.createStatement();
            String query = "select * from players_info where password=md5(\"" + password + "\");";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (username.equals(rs.getString(1))) {
                    return true;
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //Authorize with Register
    public boolean register(String username, String password) {
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from players_info");
            while (rs.next()) {
                if (username.equals(rs.getString(1))) {
                    return false;
                }
            }
            PreparedStatement pst = dbConnection.prepareStatement("insert into players_info values (?,md5(?),?);");
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setInt(3, 0);
            if (pst.executeUpdate() > 0) {
                System.out.println("inserted Successfully");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

//    static void sendMsgToOpponent(String msg, String opponent) {
//        for (ClientThread clientThread : ClientThread.clientsList) {
//            if (clientThread.opponent != null && clientThread.playerUsername == opponent) {
//                clientThread.ps.println(msg);
//            }
//        }
//    }
}
