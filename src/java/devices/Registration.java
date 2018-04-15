package devices;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;

public class Registration {
    
      private static Gson gson = new Gson ();
    
      public static String getResponce (Object[] devKeys , HashMap json) {

            String result = "0";
            try {
               
               String [] device_request = devKeys[2].toString().split("_");;
               int deviceid = Integer.parseInt(devKeys [1].toString());
               String accesskey = setAcccessKey ( deviceid , json.get(ClassConstants.gitst_device_id).toString());
               result = deviceid  + "__" +  accesskey  + "__" + Constants.conf_COMPANY;
            } catch (Exception ex) {
                 Errors.setErrors ("Registration / processRequest " + ex.toString());
            }
        
            return result;
    }
     
    
    private static String setAcccessKey ( int deviceid  , String deviceuid ) {
         
        String unique_key   = null;
        try {
            unique_key = UUID.randomUUID().toString().replaceAll("-", "");
            String query = "UPDATE `" + Constants.db_database +  "`.`devices` SET DKEY = ? , DID = ? WHERE ID = ?";
            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, unique_key);
            stmt.setString(2, deviceuid);
            stmt.setInt(3, deviceid);
            stmt.execute();
        } catch (Exception ex) {
            Errors.setErrors ("Registration / setAcccessKey " + ex.toString());
        }
        
        return unique_key;
    }
    
}
