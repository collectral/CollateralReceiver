package devices;

import assets.Constants;
import errors.Errors;
import initialize.Application;

import java.sql.ResultSet;
import java.sql.Statement;

public class ClassAccess {
    
    /**
     * Checks is the device allowed to access to database and system
     * @param id - device id 
     * @param regkey - Key to validate the device  
     * @return 
     */
    public static int [] isAccessDeviceAllowed (int id, String regkey) {
        int[] result  = new int [1];
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices` WHERE ID = " + id + " LIMIT 1 " ;
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) { 
                if (rs.getString("DKEY").equals(regkey)) {
                     result[0] = rs.getInt("ADMINID"); 
                }
            }
        } catch (Exception ex ) {
            Errors.setErrors("ClassAccess / isAccessAllowed " + ex.toString());
        }  
        return result; 
        
    }
    
    /**
     * Used only during registration 
     * @param id
     * @param regkey
     * @return 
     */
    public static boolean isRegAllowed (int id, String regkey ) {
          boolean result =false;
          
          try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices`  WHERE ID = " + id + " LIMIT 1 " ;
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) { 
                if (rs.getString("DKEY").equals(regkey)) {
                    result = true;
                }
            }
            
        } catch (Exception ex ) {
              Errors.setErrors("ClassAccess / isRegAllowed " + ex.toString());
        }  
        
        return result;
    }
    
    
    
}
