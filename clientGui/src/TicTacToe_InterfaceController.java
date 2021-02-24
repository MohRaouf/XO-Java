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
    static Client client;
    String playerName="";
    String playerPassword="";
    validation valid = new validation();
/*
    @FXML
    private FontAwesomeIcon minBtn;
    @FXML
    private FontAwesomeIcon maxBtn;
    @FXML
    private FontAwesomeIcon closeBtn;
    */
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
    public String sendData_login(){
         String Data;
         playerName     = userName.getText();
         playerPassword = password.getText();
         Data= "#login"+","+playerName+","+playerPassword;
         return Data;
    }
    public String SendData_Register(){
         String Data;
         playerName     = userName.getText();
         playerPassword = password.getText();
         Data= "#Register"+","+playerName+","+playerPassword;
         return Data;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void min(MouseEvent event) {
       // System.err.println("min");
       Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        s.setIconified(true);
    }

    @FXML
    private void max(MouseEvent event) {
        //Stage s = (Stage)((Node)event.getSource()).getScene().getWindow();
        //s.setFullScreen(true);
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
          
           /*client=new Client("127.0.0.1",4433);
           client.ps.print(sendData_login());
           System.out.println(sendData_login());*/
       }
       catch (Exception ex) {
        }
 
    }   
    
    @FXML
    private void register(){
        validation();
      /*  client=new Client("127.0.0.1",4433);
        client.start();
        client.ps.print(sendData_login());*/
        
        
    }
    
}
