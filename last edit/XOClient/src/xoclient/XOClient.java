/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoclient;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author mohpr
 */
public class XOClient extends Application {

    static ClientThread client;

    @Override
    public void start(Stage primaryStage) throws IOException {
        
        //Close all sub thread with exit the program
        primaryStage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
            System.exit(0);
        });
          
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        // Create a controller instance
        LoginController loginController = new LoginController(primaryStage);
        // Set it in the FXMLLoader
        loader.setController(loginController);
        
        primaryStage.setTitle("XO Prime");
        Scene scene = new Scene((Parent)loader.load());
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
