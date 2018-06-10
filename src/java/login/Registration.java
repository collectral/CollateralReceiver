package login;

import assets.Constants;
import assets.Encription;
import errors.Errors;
import initialize.Application;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
 
public class Registration {
     
    public static void registeradmin (HttpServletRequest request) {
        if (!Application.isUserExists()) {
            String login    = request.getParameter("login");
            String email    = request.getParameter("email");
            String password = request.getParameter("pass");
            setUser (login,   email,   password);
        }
    }
    
    private static int setUser (String login , String email ,  String password) {
          int result  = 0;
          try {
            
            String encrpass = Encription.getMD5(password);
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`users` "
                            + " (UN, PS, EM, ADMINID) "
                            + " VALUES "
                            + " (?, ?, ?, ?)" ;
            
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, login);
                stmt.setString(2, encrpass);
                stmt.setString(3, email);
                stmt.setInt   (4, 0);            
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
