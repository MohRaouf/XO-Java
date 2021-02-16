package xoserver;

import java.io.*;
import java.net.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ArrayList;
import static xoserver.FXMLDocumentController.dbConnection;

class ClientThread extends Thread {

    DataInputStream dis;
    PrintStream ps;
    String playerUsername;
    String opponent;
    FXMLDocumentController controls;
    boolean authenticated;
    static List<ClientThread> clientsList = new ArrayList<ClientThread>();

    public ClientThread(Socket clientSocket,FXMLDocumentController _controls) {
        this.controls=_controls;
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
                    System.out.println(clientMsg);
                    this.controls.received_data_area.appendText(clientMsg+"\n");
                    if (clientMsg.contains("#login") || clientMsg.contains("#register")) {
                        String username = clientMsg.split(",")[1];
                        String password = clientMsg.split(",")[2];
                        if (clientMsg.contains("#login")) {
                            //Authenticate user
                            if (authenticate(username, password)) {
                                this.playerUsername = username;
                                authenticated = true;
                                //Confirm
                                this.ps.println("#done");
                            } else {
                                this.ps.println("#no");
                            }
                        } else if (clientMsg.contains("#register")) {
                            //register user
                            if (register(username, password)) {
                                this.playerUsername = username;
                                authenticated = true;
                                //Confirm
                                this.ps.println("#done");
                            } else {
                                this.ps.println("#no");
                            }
                        }
                    } else {
                        if (authenticated) {
                            //if has opponent forward the msg to the opponent
                            sendMsgToOpponent(clientMsg, this.opponent);
                        }
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

    public boolean register(String username, String password) {
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from players_info");
            while (rs.next()) {
                if (username.equals(rs.getString(1))) {
                    return false;
                }
                PreparedStatement pst = dbConnection.prepareStatement("insert into players_info values (?,md5(?),?);");
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setInt(3, 0);
                if (pst.executeUpdate() > 0) {
                    System.out.println("inserted Successfully");
                }

            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    static void sendMsgToOpponent(String msg, String opponent) {
        for (ClientThread clientThread : ClientThread.clientsList) {
            if (clientThread.opponent != null && clientThread.playerUsername == opponent) {
                clientThread.ps.println(msg);
            }
        }
    }
}
