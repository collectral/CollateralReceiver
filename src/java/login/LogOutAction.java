package login;

import assets.Constants;
import errors.Errors;
import initialize.Application;
import java.sql.PreparedStatement;

public class LogOutAction {
        
    public static void deleteKey (Object[] userdata) {
            
            if (userdata.length == 2) {
               try {
                    int user_id = Integer.parseInt(userdata[0].toString());
                    String query = "UPDATE `" + Constants.db_database +  "`.`users` SET CK = NULL WHERE ID =  " +user_id;
                    PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
                    stmt.execute();
                } catch (Exception ex) {
                    Errors.setErrors ("LogOutAction / deleteKey " + ex.toString());
                }
            }
    }
}

