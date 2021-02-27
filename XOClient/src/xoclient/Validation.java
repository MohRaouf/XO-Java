package xoclient;

/**
 *
 * @author Aalaa Habib
 */
public class Validation {
     private static final String userName_pattern =
            "^[a-zA-Z_]{3,20}$";
     private static final String password_pattern = 
             "^[a-zA-Z0-9_]{4,20}$";
    
    //user name validation must bt  from 3 to 20 charachters only
    public boolean isValid_userName(String name){
          boolean userName_valdation=   name.matches(userName_pattern);
          if(userName_valdation)
               return true;
             else 
                return false;
    } 
    
    //password validation , it can be combination from  charachters and numbers from 8 to 20
    public boolean isValid_password(String pass){
          boolean userName_valdation=   pass.matches(password_pattern);
          if(userName_valdation)
                return true;
             else 
               return false;
    } 
  
}
