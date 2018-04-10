package login;

import assets.Constants;
import errors.Errors;
import java.sql.PreparedStatement;

public class LogOutAction {
        
    public static void deleteKey (Object[] userdata) {
          
                try {
                    System.out.println(userdata.length);
                    
                   if (userdata.length == 2) {
                        int user_id = Integer.parseInt(userdata[0].toString());
                        String query = "UPDATE `" + Constants.db_database +  "`.`users` SET CK = NULL WHERE ID =  " +user_id;
                        PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
                        stmt.execute();
                   }
                } catch (Exception ex) {
                    Errors.setErrors ("LogOutAction / deleteKey " + ex.toString());
                }
    }
}

