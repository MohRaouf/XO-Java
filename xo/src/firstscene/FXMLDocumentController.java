/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package firstscene;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author esraa abou alkassem
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private AnchorPane ap;
    @FXML
    private AnchorPane ap2;
    @FXML
    private Label login;
    @FXML
    private Button loginbtn;
    @FXML
    private AnchorPane apsign;
    @FXML
    private Label signup;
    @FXML
    private TextField signuser;
    @FXML
    private Button signupbtn;
    @FXML
    private TextField signemail;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
