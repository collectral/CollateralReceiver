package devices;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class PostData {
      
    private static Gson gson = new Gson ();
    
    public static String getResponce (Object[] deviceKeys, HashMap data) {
        String result  = "0";
    
        try {
                   
            int deviceid = Integer.parseInt(deviceKeys[1].toString());

            String formid   = data.get(ClassConstants.formid).toString();
            
            HashMap fileJson = (HashMap)data.get(ClassConstants.json);
            try {
                if (setDatabase (deviceid ,Integer.parseInt(formid), fileJson) > 0) {
                    result = "1";
                } 
            } catch (Exception ex) {
                Errors.setErrors("ServletSetData / processRequest 1 " + ex.toString());
            }
                     
                   
            
        } catch (Exception ex) {
            Errors.setErrors("ServletSetData / processRequest " + ex.toString());
        }
        
        return result;
    }
    
    private static int setDatabase (int deviceid, int formid, HashMap fileJson) {
        int result = 0;
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`data_get` "
                  + " (DEVICEID, FORMID, JSONTEXT) "
                  + " VALUES "
                  + " (?, ?, ?)" ;
            
            stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, deviceid);
            stmt.setInt(2, formid);
            stmt.setString(3, gson.toJson(fileJson));
            stmt.execute();
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (Exception ex) {
            Errors.setErrors("ServletSetData / setDatabase "  + ex.toString());
        }
        
        
        try {stmt.close();} catch (Exception ex) {}
        try {rs.close();} catch (Exception ex) {}
        
        return result;
    }
}
