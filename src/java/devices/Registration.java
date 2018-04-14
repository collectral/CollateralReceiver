package devices;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

public class Registration {
    
      private static Gson gson = new Gson ();
    
      public static String getResponce (HttpServletRequest request) {
           String result = "0";
            
            try {
                
                
                String json_string  =  request.getParameter(ClassConstants .posting_data) ; 
                System.out.println(json_string);
                
                HashMap json = gson.fromJson(json_string, HashMap.class);
                
                
                
                
//                if (device_key  != null) {
//                        String [] device_request =  device_key.split("_");;
//                        Object[] device_vals = getDeviceObject (device_key);
//                        
//                        if (device_vals != null) {
//                           int deviceid = Integer.parseInt(device_vals[0].toString());
//                           String accesskey = setAcccessKey ( deviceid , device_request[3]);
//                           result = deviceid  + "__" +  accesskey  + "__" + device_vals[2] ;
//                        }
//                }
            } catch (Exception ex) {
                 Errors.setErrors ("ServletSyncReg / processRequest " + ex.toString());
            }
        
            return result;
    }
      private static Object[] getDeviceObject (String  device_requests) {
        
            Object[] result  = null;

            try {

                String[] splitted = device_requests.split("_");

                int id1 = Integer.parseInt(splitted[0]);
                int id2 = Integer.parseInt(splitted[1]);
                int id3 = Integer.parseInt(splitted[2]);
                String query = "SELECT * FROM  `" +  Constants.db_database  
                        +  "`.`devices` WHERE DKEY = '" +id1 + "_" + id2 + "_" + id3 + "'  LIMIT 1 " ;

                PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
                //stmt.setString(1, device_requests);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) { 
                    result    = new Object[3]; 
                    result[0] = rs.getInt("ID"); 
                    result[1] = rs.getInt("ADMINID"); 
                    result[2] = Constants.conf_COMPANY; 
                }

                stmt.close();
                rs.close();
            } catch (Exception ex ) {
                Errors.setErrors("ClassAccess / isAccessAllowed " + ex.toString());
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
            Errors.setErrors ("ServletSyncReg / setAcccessKey " + ex.toString());
        }
        
        return unique_key;
    }
    
}
