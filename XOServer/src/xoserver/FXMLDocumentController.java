/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.io.IOException;
import java.net.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.sql.*;

/**
 *
 * @author mohpr
 */
public class FXMLDocumentController implements Initializable {

    public static ServerSocket myServerSocket;
    public static Connection dbConnection;

    @FXML
    public Button start_button;
    @FXML
    public TextArea available_players_area,received_data_area;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {

        //Connect to the DB
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "Eng-2873886");

        //Start listening on a separate Thread to not block the GUI
        Thread listenThread = new Thread(() -> startListening());
        listenThread.start();

        populateAvailablePlayers();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void populateAvailablePlayers() {
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from players_info");
            while (rs.next()) {
                available_players_area.appendText(rs.getString(1)+"\n");
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void startListening() {
        try {
            myServerSocket = new ServerSocket(4433); //create server socket that listens on port 4433
            while (true) {
                start_button.setDisable(true); //disable the start button, only one thread from listening to incomming
                Socket socket = myServerSocket.accept(); //blocking listening method
                new ClientThread(socket,this); //start new thread for each client
            }
        } catch (Exception e) {
            start_button.setDisable(false); //activate the start button if the listen thread exit
            e.printStackTrace();
        }
    }
}
