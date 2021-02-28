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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author mohpr
 */
public class XOClient extends Application {

    static ClientThread client;

    static class Delta {

        double x, y;
    }
    static Delta dragDelta = new Delta();

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
        Scene scene = new Scene((Parent) loader.load());

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
    }
    public static void setMoveListener(Node node,Stage primaryStage) {
        node.setOnMousePressed((MouseEvent mouseEvent) -> {
            // record a delta distance for the drag and drop operation.
            XOClient.dragDelta.x = primaryStage.getX() - mouseEvent.getScreenX();
            XOClient.dragDelta.y = primaryStage.getY() - mouseEvent.getScreenY();
        });

        node.setOnMouseDragged((MouseEvent mouseEvent) -> {
            primaryStage.setX(mouseEvent.getScreenX() + XOClient.dragDelta.x);
            primaryStage.setY(mouseEvent.getScreenY() + XOClient.dragDelta.y);
        });
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
