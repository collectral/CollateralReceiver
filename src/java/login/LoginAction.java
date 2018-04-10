package login;

import assets.Constants;
import errors.Errors;
import initialize.Application;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class LoginAction {

    /**
     * Gets user access and if it is new login puts key 
     * @param login
     * @param password
     * @return 
     */
    public static Object [] getUserKeyFromLoginPass (String login , String password) {
            Object[]  result  = null;
            try {
                if (login.length() >= Constants.login_length) {

                       password = MD5(password);
                       String query = "SELECT * FROM `" + Constants.db_database +  "`.`users` WHERE UN = ?  LIMIT 1 ";
                       
                       PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
                       stmt.setString(1, login );
                       ResultSet rs =  stmt.executeQuery();

                       while (rs.next()) {
                          String pswd =  rs.getString("PS");
                          if (password.equals(pswd)) {
                              result = new Object [2];
                              result[0] = rs.getInt("ID");
                              if (rs.getString("CK") == null || rs.getString("CK").length() < 3 ) {
                                  String new_cookie_key = generateCookieKey (rs.getInt("ID"));
                                  setCookieKey (rs.getInt("ID"), new_cookie_key);
                                  result[1] = new_cookie_key;
                              }  else {
                                  result[1] = rs.getString("CK");
                              }
                          }
                       }

                }
            } catch (Exception ex) {
                Errors.setErrors ("LoginAction / getUserKeyFromLoginPass " + ex.toString());
            }
            return result;
    }
    
    public static Object[] getUserKeyFromCookies (String cookie_key) {
          Object[]  result  = null;
          try {
          
            String [] keys = cookie_key.split("_");
            
            if (keys.length == 2) {
                    int id = Integer.parseInt(keys[0]);
                    String query = "SELECT * FROM `" + Constants.db_database +  "`.`users` WHERE ID =  " +id + " LIMIT 1 ";
                    PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
                    ResultSet rs =  stmt.executeQuery();
                    
                    while (rs.next()) {
                       String cookie =  rs.getString("CK");
                       if (cookie_key.equals(cookie)) {
                           result = new Object [2];
                           result[0] = rs.getInt("ID");
                           result[1] = rs.getString("CK");
                       }
                    }
                
            }
          } catch (Exception ex) {
             Errors.setErrors ("LoginAction / getUserKeyFromCookies " + ex.toString());
          }
           
            return result;     
    }
    
    private static void setCookieKey (int id, String cookie_key) {
               try {
                  String query = "UPDATE `" + Constants.db_database +  "`.`users` SET CK = '"  + cookie_key  + "' WHERE ID =  " +id + " ";
                  PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
                  stmt.execute();
                } catch (Exception ex) {
                  Errors.setErrors ("-->> LoginAction / setCookieKey " + ex.toString());
                }
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
}

