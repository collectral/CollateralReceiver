package login;

import assets.Constants;
import assets.Encription;
import emailsender.EmailSender;
import errors.Errors;
import initialize.Application;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
 
public class Registration {
     
    public static void registeradmin (String login , String companyname ,  String email , String fullname, String password) {
       
        
        if (!Application.isUserExists()) {
            setUser (login, companyname , email, fullname, password);
        }
        
       
    }
    
    
    
    
    private static int setUser (String login , String companyname , String email , String fullname, String password) {
          int result  = 0;
          
          try {
            
            String encrpass = Encription.getMD5(password);
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`users` "
                  + " ( UN , CM, FN, PS, EM, ADMINID ) "
                  + " VALUES "
                  + " ( ? , ?, ?, ?, ?,  ? )" ;
           
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, login);
            stmt.setString(2, companyname);
            stmt.setString(3, fullname);
            stmt.setString(4, encrpass);
            stmt.setString(5, email);
            stmt.setInt(6, 0);
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                result=rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            Errors.setErrors("Registration / setUser " + ex.toString());
        }
        
        
        return result;
    }
    
    
    
    
    
    
}
