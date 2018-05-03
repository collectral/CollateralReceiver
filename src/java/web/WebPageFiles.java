package web;

import assets.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import errors.Errors;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class WebPageFiles {
    
    private static Gson gson = new Gson ();
    
    public static ArrayList getFiles (int limit) {
        
        ArrayList result  = new ArrayList ();
        
         try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT DG.*, DVS.DESCRIPTION FROM  `" +  Constants.db_database  +  "`.`data` AS DG "
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`devices` AS DVS ON DVS.ID = DG.DEVICEID " 
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`forms` AS FRM ON FRM.ID = DG.FORMID"
                    + " ORDER BY DG.ID DESC LIMIT  " + limit ;
            ResultSet rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [4];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("DESCRIPTION");
                usr [2] = rs.getString("DESCRIPTION");
                usr [3] = rs.getDate("CDATE") + "  " +  rs.getTime("CDATE");
                result.add(usr);
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFiles  " + ex.toString());
        }  
        
        return result;
    }
    
    public static ArrayList getFiles (int formid, int limit) {
        
        ArrayList result  = new ArrayList ();
        
         try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT DG.*, DVS.DESCRIPTION  FROM  `" +  Constants.db_database  +  "`.`data` AS DG "
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`devices` AS DVS ON DVS.ID = DG.DEVICEID " 
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`forms` AS FRM ON FRM.ID = DG.FORMID "
                    + " WHERE DG.FORMID = " + formid + " ORDER BY DG.ID DESC LIMIT  " + limit ;
            ResultSet rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [4];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("DESCRIPTION");
                usr [2] = rs.getString("DESCRIPTION");
                usr [3] = rs.getDate("CDATE") + "  " +  rs.getTime("CDATE");
                result.add(usr);
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFiles  " + ex.toString());
        }  
        
        return result;
    }
    
    public static Object[] getFileJson (int fileid) {
        Object[] result  = null;
            
        Statement st = null;
        ResultSet rs = null;
        
        try {
            st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM `" +  Constants.db_database  +  "`.`data` WHERE ID = " + fileid  ;
            rs = st.executeQuery(query);
            
            while (rs.next()) { 
                result = new Object[5];
                result [0] = rs.getInt("DEVICEID");
                result [1] = rs.getInt("FORMID");
                JsonElement jelement = new JsonParser().parse(rs.getString("JSONTEXT"));
                JsonObject  jobject = jelement.getAsJsonObject();
                jobject = jobject.getAsJsonObject("filedata");
                result [2] = jobject;
                result [3] = rs.getDate("CDATE");
            }
        } catch (Exception ex) {
            Errors.setErrors("WebPageFiles / getFileJson  " + ex.toString());
        }   
        
        try {st.close();} catch (Exception ex) {}
        try {rs.close();} catch (Exception ex) {}
     
        return result; 
    }
    
    public static ArrayList getFormList() {
        
        ArrayList result = new ArrayList ();
        Statement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms` " ;
            st = Constants.dbConnection.createStatement();
            rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [2];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("NAME");
                result.add(usr);
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFormList  " + ex.toString());
        }  
        
        try { st.close();} catch (Exception ex){}
        try { rs.close();} catch (Exception ex){}
        
        return result;
    }
    
    
    public static String getFieldValue (String fieldid, JsonObject obj) {
        String result  = "";
        try {
           JsonObject fieldObj = obj.getAsJsonObject(fieldid + "");
           JsonElement element = fieldObj.get("VALUE");
           result = element.getAsString();
        } catch (Exception ex) {
            Errors.setErrors("WebPageFiles / getFieldvalue " + ex.toString());
        }
        return result;
    } 
    
    
    public static String getFormNameAndDisplay (int formid, JsonObject obj) {
        String result  = "";
        
        Statement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms` WHERE ID = " + formid ;
            st = Constants.dbConnection.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) { 
               result +=  rs.getString("NAME");
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFormList  " + ex.toString());
        }  
        
        result += " : ";
        
        try { st.close();} catch (Exception ex){}
        try { rs.close();} catch (Exception ex){}
        
        try {
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms_fields` WHERE DISPL = 1 AND FID =  " + formid ;
            st = Constants.dbConnection.createStatement();
            rs = st.executeQuery(query);
            
            while (rs.next()) { 
                result += getFieldValue (rs.getInt("ID")+ ""  ,  obj) + "  ";
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFormList  " + ex.toString());
        }  
        
        try { st.close();} catch (Exception ex){}
        try { rs.close();} catch (Exception ex){}
        
        return result;
    }
    
    private String demo(String jsonLine) {
           JsonElement jelement = new JsonParser().parse(jsonLine);
           JsonObject  jobject = jelement.getAsJsonObject();
           jobject = jobject.getAsJsonObject("data");
           JsonArray jarray = jobject.getAsJsonArray("translations");
           jobject = jarray.get(0).getAsJsonObject();
           String result = jobject.get("translatedText").getAsString();
           return result;
    }
    
    
    public static ArrayList getImages (int fieldid, int fileid) {
        ArrayList result = new ArrayList ();    
        
        
        Statement st = null;
        ResultSet rs = null;
        try {
            String query = "SELECT * FROM  `" +  Constants.db_database  
                    +  "`.`data_images` WHERE FILEID = " + fileid + " AND FIELDID = " + fieldid;
            
            st = Constants.dbConnection.createStatement();
            rs = st.executeQuery(query);
            Object[] igOpbj ;
            
            while (rs.next()) { 
                 igOpbj = new Object[3];
                 igOpbj[0] = rs.getInt("ID");
                 igOpbj[1] = rs.getString("NAME");
                 igOpbj[2] = rs.getString("CONTENT");
                 result.add(igOpbj);
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getImages  " + ex.toString());
        }  
        
        try { st.close();} catch (Exception ex){}
        try { rs.close();} catch (Exception ex){}
        
        
        return result;
    }   
    
}
