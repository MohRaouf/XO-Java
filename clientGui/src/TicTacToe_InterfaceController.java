
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aalaa Habib
 */
public class TicTacToe_InterfaceController implements Initializable {
    validation valid = new validation();

    @FXML
    private FontAwesomeIcon minBtn;
    @FXML
    private FontAwesomeIcon maxBtn;
    @FXML
    private FontAwesomeIcon closeBtn;
    
    @FXML
    public TextField userName; 
    public TextField password;
    public Button loginBtn;
    public Button registerBtn;
    public Label errorLb;
    public Label passError;
    
    public void clear(){
        errorLb.setText("");
        passError.setText("");
    }
    public void validation(){
          boolean check_name, check_pass;
          clear();
          check_name= valid.isValid_userName(userName.getText());
          check_pass=valid.isValid_password(password.getText()); 
          if(!check_name)
             errorLb.setText("Name must be charachters only [3,20] digit");
          if(!check_pass)
             passError.setText("Password must be 8 digit at least upTo 20");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void min(MouseEvent event) {
        System.err.println("min");
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setFullScreen(true);
    }
    

    @FXML
    private void close(MouseEvent event) {
        Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.close();
    }
    
    @FXML
      private void login(ActionEvent event)
    {
       try{
           validation();
           clientSocket client = new clientSocket(); 
           
       }
       catch (Exception ex) {
        }
 
    }   
    
    @FXML
    private void register(){
        validation();
    }
    
}
