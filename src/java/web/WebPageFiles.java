package web;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class WebPageFiles {
    
    private static Gson gson = new Gson ();
    
    public static ArrayList getFiles (int limit) {
        
        ArrayList result  = new ArrayList ();
        
         try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT DG.*, USR.FN, FRM.NAME FROM  `" +  Constants.db_database  +  "`.`data_get` AS DG "
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`users` AS USR ON USR.ID = DG.USERID " 
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`forms` AS FRM ON FRM.ID = DG.FORMID"
                    + " ORDER BY DG.ID DESC LIMIT  " + limit ;
            ResultSet rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [4];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("FN");
                usr [2] = rs.getString("NAME");
                usr [3] = rs.getDate("RDATE");
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
            String query = "SELECT DG.*, USR.FN, FRM.NAME FROM  `" +  Constants.db_database  +  "`.`data_get` AS DG "
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`users` AS USR ON USR.ID = DG.USERID " 
                    + " LEFT JOIN  `" +  Constants.db_database  +  "`.`forms` AS FRM ON FRM.ID = DG.FORMID "
                    + " WHERE DG.FORMID = " + formid + " ORDER BY DG.ID DESC LIMIT  " + limit ;
            ResultSet rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [4];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("FN");
                usr [2] = rs.getString("NAME");
                usr [3] = rs.getDate("RDATE");
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
            String query = "SELECT * FROM `" +  Constants.db_database  +  "`.`data_get` WHERE ID = " + fileid  ;
            rs = st.executeQuery(query);
            
            while (rs.next()) { 
                result = new Object[5];
                result [0] = rs.getInt("USERID");
                result [1] = rs.getInt("DEVICEID");
                result [2] = rs.getInt("FORMID");
                HashMap hs = gson.fromJson(rs.getString("JSON"), HashMap.class);
                result [3] = hs;
                result [4] = rs.getDate("RDATE");
            }
        } catch (Exception ex) {
            Errors.setErrors("WebPageFiles / getFiles  " + ex.toString());
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
            rs = st.executeQuery(query);
            
            Object [] usr = null;
            while (rs.next()) { 
                usr = new Object [2];
                usr [0] = rs.getInt("ID");
                usr [1] = rs.getString("NAME");
                result.add(usr);
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("WebPageFiles / getFiles  " + ex.toString());
        }  
        
        try { st.close();} catch (Exception ex){}
        try { rs.close();} catch (Exception ex){}
        
        return result;
    }
    
    
    public static HashMap convertToHashMap (String key , HashMap hashmap) {
       HashMap result = null;
        
       try {
          result = gson.fromJson(hashmap.get(key).toString(), HashMap.class)  ;
       } catch (Exception ex) {
           Errors.setErrors("WebPageFiles / convertToHashMap " + ex.toString());
       }
       
       return result;
    }
   
    
     
    
}
