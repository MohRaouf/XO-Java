package xo;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SecController implements Initializable {
    
     private static final String userName_pattern =
            "^[a-zA-Z_]{3,20}$";
     private static final String password_pattern = 
             "^[a-zA-Z0-9_]{8,20}$";
    
@FXML
    public TextField userName; 
    public TextField password;
    public Button loginbtn;
    
    //user name validation must bt  from 3 to 20 charachters only
    public void isValid_userName(String name){
          boolean userName_valdation=   name.matches(userName_pattern);
          if(userName_valdation)
                 System.err.println("valid userName");
             else 
                 System.err.println("The User Name must be at least 3 charachters ");
    } 
    
    //password validation , it can be combination from  charachters and numbers from 8 to 20
    public void isValid_password(String pass){
          boolean userName_valdation=   pass.matches(password_pattern);
          if(userName_valdation)
                 System.err.println("valid password");
             else 
                 System.err.println("The Password must be 8 charachters");
    } 
    
    @FXML
     private void login(ActionEvent event)
    {
       try{
           isValid_userName(userName.getText());
           isValid_password(password.getText()); 
       }
       catch (Exception ex) {
        }
 
    }   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
