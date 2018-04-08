package servers;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import initialize.Application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

public class ServerAccess {
    
    private static Gson gson = new Gson ();
    
    public static String getAdminServerKey (String username , String password) {
        String result  = null; 
        
        try {
            int userid = 0;
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`users` WHERE UN = ? LIMIT 1 " ;
            PreparedStatement st = Constants.dbConnection.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("PS").equals(password)) {
                     userid = rs.getInt("ID"); 
                }
            }
            rs.close();
            st.close();
            
            if (userid > 0) {
               result = generateCookieKey (userid);
               query = "UPDATE FORM `" +  Constants.db_database  +  "`.`users` SET AKEY = ? WHERE ID = " + userid;
               st = Constants.dbConnection.prepareStatement(query);
               st.setString(1, result);
               st.executeQuery(query);
               rs.close();
               st.close();
            }
            
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getAdminServerKey " + ex.toString());
        }  
        return result; 
    }
      
    public static boolean isAccessAllowed (int id, String adminkey, String connectionip) {
        
        boolean result  = false; 
        try {
            
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`users` WHERE ID = " + id + " LIMIT 1 " ;
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                if (rs.getString("AKEY").equals(adminkey)) {
                    String sip = rs.getString("SERVERIP");
                    if ( sip != null) {
                       if (sip.equals(connectionip)) {
                          result  = true; 
                       }
                    } else {
                          result  = true; 
                    }
                }
            }
            
            rs.close();
            st.close();
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getAdminServerKey " + ex.toString());
        }  
        
        return result; 
    }
    
    
    public static String getAllData (int adminid) {
        
        String result = null;
        try {
            HashMap  rsmap = new HashMap ();
            rsmap.put("AllDevices", getGetAllDevices (adminid));
            rsmap.put("DeviceGroupMapping", getDeviceGroupMapping (adminid));
            rsmap.put("AllForms",  getGetAllForms (adminid));
            rsmap.put("AllGroups", getGetAllGroups (adminid));
            result = gson.toJson(rsmap);
        } catch (Exception ex) {
            Errors.setErrors("ServerAccess / getAllData "  + ex.toString());
        }
        
        return result;
    }
    
    
    /**
     * Retrieves all data about Devices
     * @return 
     */
    private static String getGetAllDevices (int adminid) {
        String result = null;
        
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices` WHERE ID = " + adminid + " LIMIT 1 " ;
            ResultSet rs = st.executeQuery(query);
            HashMap devices =  new HashMap ();
            
            while (rs.next()) {
                  devices.put("DKEY", "DKEY");
                  devices.put("COMPANY", "COMPANY");
                  devices.put("DID" , "DID");
                  devices.put("ID"  , "ID");
                  devices.put("DESCRIPTION", "DESCRIPTION");
            }
            
            result = gson.toJson(devices);
            rs.close();
            st.close();
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getGetAllDevices "  + ex.toString());
        }  
        
        return result;
    }
    
    private static HashMap getDeviceGroupMapping (int adminid) {
        HashMap result = null;
            
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group_connectaion` WHERE ADMINID = " + adminid ;
            ResultSet rs = st.executeQuery(query);
            result =  new HashMap ();
            HashMap dmap;
            while (rs.next()) {
                dmap =  new HashMap ();
                dmap.put("DEVICEID", rs.getInt("DEVICEID"));
                dmap.put("GROUPID",  rs.getInt("GROUPID"));
                result.put(rs.getInt("ID") + "",  dmap)  ;
            }
            rs.close();
            st.close();
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getDeviceGroupMapping " + ex.toString());
        }  
        
        return result;
    } 
    
    private static HashMap getGetAllForms (int adminid) {
        HashMap result = null;
        
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`forms` WHERE ADMINID = " + adminid ;
            ResultSet rs = st.executeQuery(query);
            result =  new HashMap ();
            HashMap form;
            while (rs.next()) {
                form = getSingleForm (rs.getInt("ID"));
                form.put("GROUPID", rs.getInt("GROUPID"));
                form.put("FORMID",  rs.getInt("ID"));
                result.put(rs.getInt("ID") + "", form)  ;
            }
            rs.close();
            st.close();
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getGetAllForms " + ex.toString());
        }  
        
        return result;
    }
    
    private static HashMap getGetAllGroups (int adminid) {
        HashMap result = null;
          
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group`  WHERE ADMINID = " + adminid ;
            
            ResultSet rs = st.executeQuery(query);
            
            result =  new HashMap ();
            HashMap form;
            
            while (rs.next()) {
                form = new HashMap ();
                form.put("GROUPID", rs.getInt("ID"));
                form.put("NAME",  rs.getString("NAME"));
                result.put(rs.getInt("ID") + "", form)  ;
            }
            
            
            rs.close();
            st.close();
        } catch (Exception ex ) {
            Errors.setErrors("ServerAccess / getGetAllGroups " + ex.toString());
        }  
        
        return result;
    }
      
    private static String generateCookieKey (int id) {
        UUID uid = UUID.randomUUID();
        String uuids = id + "_" + uid.toString().replaceAll("-", "");
        return uuids;
    }
    
    private static String MD5(String md5) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                byte[] array = md.digest(md5.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                  sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
                }
                return sb.toString();
            } catch (java.security.NoSuchAlgorithmException e) {
            }
         return null;
     }
    
    /**
     * Generate single Form 
     * @param id
     * @param name
     * @formidstoget ID of forms which should be received 
     * @return 
     */
    private static HashMap getSingleForm (int fid) {
        HashMap singleFormResult  = new HashMap();
        
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms_sections` WHERE FID = '" + fid + "'";
           rs = stmt.executeQuery(query);
           
           HashMap sectionNames = new HashMap ();
           
           while (rs.next()) {   
               int section_number = rs.getInt("FP");
               HashMap section =  getSection (fid, rs.getInt("ID"), section_number); 
               
               if (section != null ) {
                   singleFormResult.put(rs.getInt("ID") + "", section);
                   sectionNames.put(rs.getInt("ID") + "", rs.getString("NAME"));
               }
               
               
           }
           
           
           if (singleFormResult.size() > 0) {
              singleFormResult.put("NAMES", sectionNames);
           }
           
           
        } catch (Exception ex) {
           Errors.setErrors ("ServerAccess / getSingleForm /" + ex.toString());
        }   
        
        try {if ( rs != null ) {rs.close();} }catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
       
        return singleFormResult;
        
    } 
    
    private static HashMap getSection (int fid, int sid, int section_number) {
        
        HashMap singleSection  = new HashMap();
        
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms_fields` WHERE FID = '" + fid + "' AND SID = '" + sid +  "'";
           rs = stmt.executeQuery(query);
           
           while (rs.next()) {   
                HashMap singleField = new HashMap();
                singleField.put("ID", rs.getInt("ID"));
                singleField.put("NAME", rs.getString("NAME"));
                singleField.put("DVAL", rs.getString("DVAL"));
                singleField.put("TYPE", rs.getInt("TYPE"));
                singleField.put("MAND", rs.getInt("MAND"));
                singleField.put("DISPL", rs.getInt("DISPL"));
                
                singleSection.put(rs.getInt("ID") + "", singleField);
               
           }
           
           if (singleSection.isEmpty()) {
               singleSection  = null;
           } else {
              singleSection.put("SN", section_number + "");
           }
           
        } catch (Exception ex) {
           Errors.setErrors ("ServerAccess / getSingleForm /" + ex.toString());
        }   
        
        try {if ( rs != null ){ rs.close(); }}catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
       
        return singleSection;
        
    }
    
}
