package devices;

import assets.Constants;

import errors.Errors;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class GetForms {
      
    public static String getResponce ( Object[] deviceKeys) {
        
        String result  = "0";
        
        try {
            String groupList = getGroupIDsInString (Integer.parseInt(deviceKeys[1].toString()));
            result = "";
            ArrayList formsIds = getFormsArray (groupList, Integer.parseInt(deviceKeys[3].toString()));

            for (int fil = 0 ; fil <formsIds.size() ; fil++ ) {
                try {
                   result = result +  formsIds.get(fil) + ",";
                } catch (Exception ex1) {
                   Errors.setErrors(ex1.toString());
                }
            }
            
            result = result.substring(0, result.length() - 1);
        } catch (Exception ex) {
           Errors.setErrors("GetForms / processRequest " + ex.toString());
        }
        
        return result;
    }
    
    
    public static String getGroupIDsInString (int deviceid) {
        String result = "";
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
           stmt = Constants.dbConnection.createStatement();
           String query  = "SELECT * FROM `" + Constants.db_database + "`.`devices_group_connectaion`"
                   + " WHERE DEVICEID = "  +  deviceid  ;
           
           rs = stmt.executeQuery(query);
           while (rs.next()) {   
              result +=  rs.getInt("GROUPID") + ",";
           }
           
           result = result.substring(0, result.length() - 1);
           
        } catch (Exception ex) {
            Errors.setErrors ("GetForms / getGroupLists " + ex.toString());
        }   
        try {rs.close();}catch(Exception ex){} 
        try {stmt.close();}catch(Exception ex){} 
        
        return result;
    }
    
    
    public static ArrayList getFormsArray (String grouplist, int adminid ) {
        ArrayList result = new ArrayList ();
        ResultSet rs = null;
        Statement stmt = null;
        try {
           stmt = Constants.dbConnection.createStatement();
           String query  = "SELECT * FROM `" + Constants.db_database + "`.`forms`"
                   + " WHERE ADMINID = "  + adminid + " AND GROUPID IN ("  + grouplist + ")" ;
           
           rs = stmt.executeQuery(query);
           while (rs.next()) {  
               result.add(rs.getInt("ID"));
           }
        } catch (Exception ex) {
            Errors.setErrors ("GetForms / getFormsArray " + ex.toString());
        }   
        
        try {rs.close();}catch (Exception ex){} 
        try {stmt.close();}catch (Exception ex){} 
        
        return result;
    }
    
   
   
    
}
