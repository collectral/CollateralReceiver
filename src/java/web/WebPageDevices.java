package web;

import assets.Constants;
import assets.HTTPSPost;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
 
public class WebPageDevices {
    
    /**
     * OK 
    */
    public static int insertGroup (String groupname, HttpServletRequest request) {
        
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);   
        int result  = 0 ;
        
        try {
            
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`devices_group` "
                  + " ( NAME , ADMINID ) "
                  + " VALUES "
                  + " ( ? , ? )" ;
           
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, groupname);
            stmt.setInt(2,  Integer.parseInt(id_key[0].toString()));
            
            stmt.execute();
            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()){
                result=rs.getInt(1);
            }
            
            rs.close();
            stmt.close();
        } catch (Exception ex) {
            Errors.setErrors("WebPageDevices / insertUser "  + ex.toString());
        }
        
        return result;
    }
    
    /**
     * Is OK
     * @param request
     * @return 
     */
    public static ArrayList getAllGroup (HttpServletRequest request) {
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        ArrayList groupList  = new ArrayList ();
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group` WHERE  ADMINID = " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            Object [] obj = null;
            while (rs.next()) { 
                obj    = new Object[3];
                obj[0] = rs.getInt("ID");
                obj[1] = rs.getString("NAME");
                
                groupList.add(obj);
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageDevices / isAccessAllowed " + ex.toString());
        }  
        
        return  groupList;
    }
    
    /**
     * OK
     * @param user_id
     * @param request
     * @return 
     */
    public static ArrayList getDeviceList (int user_id, int groupid,  HttpServletRequest request) {
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        ArrayList result = new ArrayList ();
         
        try {
            
            Statement st = Constants.dbConnection.createStatement();
            String query;
            
            if (groupid > 0) {
              
               String groupList = "" ;
                
               String select_group = "SELECT  * FROM  `" +  Constants.db_database  +  "`.`devices_group_connectaion`"
                      + "  WHERE  ADMINID = " + id_key[0] + " AND GROUPID = " + groupid;
                
               ResultSet rs = st.executeQuery(select_group);
               while (rs.next()) { 
                   groupList += rs.getString("DEVICEID");
                   if (!rs.isLast()) {
                      groupList +=  ", " ;
                   }
               }
               
               query = "SELECT  * FROM `" +  Constants.db_database  +  "`.`devices`"
                       + " WHERE  ADMINID = " + id_key[0] + " AND ID IN ("  + groupList +  ")";
               
               rs.close();
               
               
            } else {
              query = "SELECT  * FROM `" +  Constants.db_database  +  "`.`devices` WHERE  ADMINID = " + id_key[0];
            }
            
            
            ResultSet rs = st.executeQuery(query);
            Object [] obj = null;
            
            while (rs.next()) { 
                obj    = new Object[5];
                obj[0] = rs.getInt("ID");
                obj[1] = rs.getString("DESCRIPTION");
                obj[2] = rs.getString("DKEY");
                obj[3] = rs.getString("DID");
                obj[4] = Constants.conf_COMPANY;
                result.add(obj);
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageDevices / isAccessAllowed " + ex.toString());
        }  
        
        return result; 
    }
    
    /**
     * 
     * @param groupid
     * @param request
     * @return 
     */
    public static String getDeviceGroupName (int groupid, HttpServletRequest request) {
         
        String result = "";
        
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group` WHERE ID = "  + groupid +  " AND ADMINID = " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) { 
               result = rs.getString("NAME");
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageDevices / isAccessAllowed " + ex.toString());
        }  
        
        return result;
    }
    
    public static void generateDevice (HttpServletRequest request) {
         Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
         
         int groupid = 0;
         
         try {
             groupid =  Integer.parseInt(request.getParameter("groupid"));
         } catch (Exception ex) {
             Errors.setErrors("WebPageDevices / enerateDevice 1 " + ex.toString());
         }
          
         String description  = "";
         try {
             description =  request.getParameter("description");
         } catch (Exception ex) {
             Errors.setErrors("WebPageDevices / enerateDevice 1 " + ex.toString());
         }
        
         try {
             
            String select_compay_name = "SELECT * FROM `" +  Constants.db_database  +  "`.`users` WHERE ID = " + id_key[0] ;
            Statement st = Constants.dbConnection.createStatement();
            ResultSet rs = st.executeQuery(select_compay_name);
            
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`devices` "
                  + " (DKEY, ADMINID, DESCRIPTION ) "
                  + " VALUES "
                  + " (?, ?, ?)" ;
            
            String[] key = getDeviceKey(request);
            
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, key[0]);
                stmt.setInt(2, Integer.parseInt(id_key[0].toString()));
                stmt.setString(3, description);
                stmt.execute();
               
            int insertedid = 0;    
            ResultSet rss = stmt.getGeneratedKeys();
            if (rss.next()){
                 insertedid=rss.getInt(1);
            }
            
            if (insertedid > 0 && groupid > 0) {
             
                String insert_group = "INSERT INTO `" +  Constants.db_database  +  "`.`devices_group_connectaion` "
                         + " (DEVICEID, GROUPID, ADMINID ) "
                         + " VALUES "
                         + " ( ?, ? , ? )" ;
                
                PreparedStatement stmt_group  = Constants.dbConnection.prepareStatement(insert_group);
                       stmt_group.setInt(1, insertedid);
                       stmt_group.setInt(2, groupid);
                       stmt_group.setInt(3, Integer.parseInt(id_key[0].toString()));
                       stmt_group.execute();  
                       stmt_group.close();    
            } 
             
            rss.close();
            stmt.close();   
            st.close();
            rs.close();
           
        } catch (Exception ex) {
            Errors.setErrors("WebPageDevices / generateDevice "  + ex.toString());
        }
   }
    
   private static String[] getDeviceKey(HttpServletRequest request) {
       
       String[] result = null;
       try {
            String select_compay_name = "SELECT * FROM `" +  Constants.db_database  +  "`.`conf` WHERE TYPE = 'SERVERKEY' LIMIT 1 "  ;
            Statement st = Constants.dbConnection.createStatement();
            ResultSet rs = st.executeQuery(select_compay_name);
            
            String severurl =  WebConstants.getContextFullURL(request);
            
            rs.close();
            st.close();
            
            if (Constants.conf_SERVERKEY != null && severurl != null) {
                result = HTTPSPost.sendPost(Constants.collectralurl, Constants.conf_SERVERKEY  , severurl) ;
            }
            
       } catch (Exception ex){
           Errors.setErrors("WebPageDevices / getDeviceKey() " + ex.toString());
       }
       
       return result;
   } 
  
    
   public static void deleteSingleDevice (HttpServletRequest request) {
       
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        try {
            int device_id = Integer.parseInt(request.getParameter("deviceid")) ;
            String query  = " DELETE FROM  `" +  Constants.db_database  +  "`.`devices` WHERE ID =" + device_id + " AND  ADMINID = " + id_key[0];
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
            stmt.execute();
            
            query  = " DELETE FROM  `" +  Constants.db_database  +  "`.`devices_group_connectaion` WHERE DEVICEID =" + device_id + " AND  ADMINID = " + id_key[0];
            stmt  = Constants.dbConnection.prepareStatement(query);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            Errors.setErrors("WebPageDevices / deleteSingleDevice "  + ex.toString());
        }
    }
    
    public static void deleteSingleGroup (HttpServletRequest request) {
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        int groupid = 0;
        try {
            groupid = Integer.parseInt(request.getParameter("groupid")) ;
        } catch (Exception ex){
            Errors.setErrors("WebPageDevices / deleteSingleGroup  1 " + ex.toString());
        }
        
        if (groupid > 0) {
            try {
               String query  = "DELETE FROM  `" +  Constants.db_database  +  "`.`devices_group` WHERE ID = " + groupid +  " AND ADMINID = " + id_key[0];
               PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
               stmt.execute();
               stmt.close();
            } catch (Exception ex) {
               Errors.setErrors("WebPageDevices / deleteSingleGroup 2 "  + ex.toString());
            }
        }
    }
    
    public static void updateGroup (HttpServletRequest request){ 
         
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        int group_id = 0;
        try {
           group_id = Integer.parseInt(request.getParameter("groupid"));
        } catch (Exception ex) {
            Errors.setErrors( "WebPageDevices / updateGroup  " + ex.toString());
        }
        
        if (group_id > 0) {
        
                try {
                    String query = "UPDATE   `" +  Constants.db_database  +  "`.`devices_group` SET "
                          + " NAME = ? "
                          + " WHERE  "
                          + " ID = ? AND ADMINID = ? " ;

                    PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
                        stmt.setString(1, request.getParameter("groupname"));
                        stmt.setInt(2, group_id);
                        stmt.setInt(3, Integer.parseInt( id_key[0].toString()) );
                        stmt.execute();
                        stmt.close();

                } catch (Exception ex) {
                    Errors.setErrors("WebPageDevices / updateGroup "  + ex.toString());
                }
        }
    }
    
    
    public static String diviceDescription (int device_id, HttpServletRequest request ) {
          
        String result = "";
        
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices`  WHERE ID = "  + device_id +  " AND ADMINID = " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) { 
               result = rs.getString("DESCRIPTION");
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageDevices / isAccessAllowed " + ex.toString());
        }  
        
        return result;
    }
    
    public static void editDevice (HttpServletRequest request) {
             
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        try {
            String deicedescription = request.getParameter("description");
            int deviceid = Integer.parseInt(request.getParameter("deviceid")) ;
            int groupid  = Integer.parseInt( request.getParameter("groupid"));
            
            String query = "UPDATE   `" +  Constants.db_database  +  "`.`devices` SET "
                  + " DESCRIPTION = ? "
                  + " WHERE  "
                  + " ID = " + deviceid + " AND ADMINID = " + id_key[0] ;

            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
                stmt.setString(1,  deicedescription);
                stmt.execute();
                stmt.close();


            query = "DELETE FROM `" +  Constants.db_database  +  "`.`devices_group_connectaion` "
                    + " WHERE DEVICEID = " + deviceid + " AND ADMINID = " + id_key[0] ;    
            stmt  = Constants.dbConnection.prepareStatement(query);   
            stmt.execute();


            query = "INSERT INTO `" +  Constants.db_database  +  "`.`devices_group_connectaion` "
                 + " (DEVICEID, GROUPID, ADMINID ) "
                 + " VALUES "
                 + " ( ?, ? , ? )" ;


            stmt = Constants.dbConnection.prepareStatement(query);
            stmt.setInt(1, deviceid);
            stmt.setInt(2, groupid);
            stmt.setInt(3, Integer.parseInt(id_key[0].toString()));
            stmt.execute();  
            stmt.close();
                
        } catch (Exception ex ){
            Errors.setErrors("WebPageDevices / editDevice " + ex.toString());
        }
        
        
    }
    
    
    public static int getDeviceGroupID (int deviceid, HttpServletRequest request) {
        int result  = 0;
        
        Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);
        try {
            Statement st = Constants.dbConnection.createStatement();
            String query = "SELECT * FROM  `" +  Constants.db_database  +  "`.`devices_group_connectaion` WHERE DEVICEID = "  + deviceid +  " AND ADMINID = " + id_key[0];
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) { 
               result = rs.getInt("GROUPID");
            }
            st.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("WebPageDevices / isAccessAllowed " + ex.toString());
        }  
        
         
        return result;
    }
    
}
