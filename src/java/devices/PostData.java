package devices;

import assets.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PostData {
      
    private static Gson gson = new Gson ();
    
    public static String getResponce (Object[] deviceKeys, JsonObject data) {
        String result  = "0";
        try {
            
            JsonObject filedata = data.getAsJsonObject("filedata");
            data.remove("filedata");
            int formid = Integer.parseInt(data.get(ClassConstants.formid).getAsString() );
            String deviceid = deviceKeys[1].toString();
            
            int fileid = Integer.parseInt(filedata.get("IMDBID").getAsString());
            filedata.remove("IMDBID");
            
            System.out.println("fileid   " +fileid );
            
            JsonObject images = filedata.getAsJsonObject("IMAGES");
            filedata.remove("IMAGES");
            data.add("filedata", filedata);
            
            System.out.println(" data " + data.toString());
            
            
            
            int res = setDatabase (fileid, deviceid, formid , data.toString());
            if (res > 0) {
                setImages (res,  images);
            } 
            
            if (res > 0) {
                result = res + "" ;
            } 
            
        } catch (Exception ex) {
            Errors.setErrors("PostData / getResponce " + ex.toString());
        }
        return result;
    }
    
    
    private static void setImages (int fileid,  JsonObject images) {
        
        Set<Entry<String, JsonElement>> entrySet =  images.entrySet();
        for(Map.Entry<String,JsonElement> entry : entrySet){
            
            String key = entry.getKey();
            String img = images.get(entry.getKey()).toString();
            String[] keSet = key.split("_");
            
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {

                int FIELDID  = Integer.parseInt(keSet[0]);
                String query = "DELETE FROM `" +  Constants.db_database  +  "`.`data_images` "
                        + " WHERE FILEID = " + fileid  + " AND  FIELDID = "  + FIELDID     ;
                
                
                stmt = Constants.dbConnection.prepareStatement(query);
                stmt.execute();
                
                query = "INSERT INTO `" +  Constants.db_database  +  "`.`data_images` "
                      + " (FILEID, FIELDID, NAME, CONTENT) "
                      + " VALUES "
                      + " (?, ?, ?, ?)" ;

                stmt  = Constants.dbConnection.prepareStatement(query);
                stmt.setInt(1, fileid );
                stmt.setInt(2, FIELDID);
                stmt.setString(3, keSet[1]);
                stmt.setString(4, img);
                stmt.execute();

            } catch (Exception ex) {
                Errors.setErrors("PostData / setImages  "  + ex.toString());
            }

            try {stmt.close();} catch (Exception ex) {}
            try {rs.close();} catch (Exception ex) {}
        }
    }
    
    
    private static int setDatabase (int fileid, String deviceid, int formid, String fileJson) {
        int result = 0;
        
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`data` "
                  + " (DEVICEID, FORMID, JSONTEXT) "
                  + " VALUES "
                  + " (?, ?, ?)" ;
            
            stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, Integer.parseInt(deviceid));
            stmt.setInt(2, formid);
            stmt.setString(3, fileJson);
            stmt.execute();
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (Exception ex) {
            Errors.setErrors("PostData / setDatabase "  + ex.toString());
        }
        
        try {stmt.close();} catch (Exception ex) {}
        try {rs.close();} catch (Exception ex) {}
        
        return result;
    }
}
