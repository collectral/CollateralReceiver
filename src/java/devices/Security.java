package devices;

import assets.Constants;
import assets.Encription;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class Security {
    
    private static Gson gson = new Gson ();
    
    public static Object[] getKey (HttpServletRequest request) {
        Object[] result = null;    
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            String ident = request.getParameter("IDENT");
             
            String query ;
            
            String posting = request.getParameter(ClassConstants.posting);
            
            
            if (posting != null && posting.equals(ClassConstants.posting_registration)) {
                query = "SELECT * FROM `" + Constants.db_database +"`.`devices` WHERE DKEY LIKE ?";
                stmt = Constants.dbConnection.prepareStatement(query);
                stmt.setString(1, ident + "%");
                rs = stmt.executeQuery();
            } else {
                query = "SELECT * FROM `" + Constants.db_database +"`.`devices` WHERE ID = ?";
                stmt = Constants.dbConnection.prepareStatement(query);
                stmt.setInt(1, Integer.parseInt(ident));
                rs = stmt.executeQuery();
            }
            
            if (rs!=null) {
                while (rs.next()) { 
                     result  = new Object[4];
                     result[1] = rs.getInt("ID");
                     result[2] = rs.getString("DKEY");
                     result[3] = rs.getInt("ADMINID");
                }
            }
            

        } catch (Exception ex) {
             Errors.setErrors ("Security / getKey " + ex.toString());
        }   
        
        try {if ( rs != null ){ rs.close(); }}catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
        
        try {
            if (request.getParameter(ClassConstants.posting).equals(ClassConstants.posting_registration)) {
               result[0] = result[2].toString().split("_")[2];
            } else {
               result[0] = result[2].toString();
            }
        }catch (Exception ex) {
            Errors.setErrors("Security / getKey  3  " + ex.toString());
        }
        
        return result;
    }
    
    public static String getHashDataDecripted (String key , HttpServletRequest request) {
        String result = null;
        try {
            String encriptedString = request.getParameter("DATA");
            String decreptedString = Encription.getDecriptedString(encriptedString, key);
            result   = Encription.textMixCleaner(decreptedString);
        } catch (Exception ex) {
            Errors.setErrors("Security / getHashDataDecripted " + ex.toString());
        }
        return result;
    } 
    
    
}
