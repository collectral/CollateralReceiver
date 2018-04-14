package web;

import assets.Constants;
import assets.Encription;
import errors.Errors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.http.HttpServletRequest;

public class WebPageSettings {
    
    public static String[] getUserDetails (HttpServletRequest request) {
        
        String[] result = null;
        try {
            Object[] userdata = (Object[]) request.getAttribute(Constants.user_attribute);
            String query = "SELECT * FROM `" + Constants.db_database +  "`.`users` WHERE ID = " + userdata[0];
            PreparedStatement st = Constants.dbConnection.prepareStatement(query);
            ResultSet rs = st.executeQuery(query);
        
            while(rs.next()) {
                result    = new String[3];
                result[0] = rs.getString("UN");
                result[1] = rs.getString("PS");
                result[2] = rs.getString("EM");
            }
            
            rs.close();
            st.close();
            
        } catch (Exception ex) {
            Errors.setErrors("WebPageSettings / getUserDetails " + ex.toString());
        }
        
        if (result == null) {
            result = new String [3];
            result[0] = "";
            result[1] = "";
            result[2] = "";
        }
        
        return result;
    }
    
    public static void setUserDetails (HttpServletRequest request) {
        try {
           String username = request.getParameter("login");
           String password = request.getParameter("password");
           String email    = request.getParameter("email");
            
           Object[] userdata = (Object[]) request.getAttribute(Constants.user_attribute);
           String query = "";
           
           if (password.length() > 5) {
               query = "UPDATE `" + Constants.db_database +  "`.`users` SET UN = ? , PS = ? , EM = ? WHERE ID = " + userdata[0];
           } else {
               query = "UPDATE `" + Constants.db_database +  "`.`users` SET UN = ? , EM = ?  WHERE ID = " + userdata[0];
           }
            
           PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
           
           if (password.length() > 5) {
               password = Encription.getMD5(password);
               stmt.setString(1, username);
               stmt.setString(2, password);
               stmt.setString(3, email);
           } else {
               stmt.setString(1, username);
               stmt.setString(2, email);
           }
           
           stmt.execute();
           stmt.close();
           
        } catch (Exception ex) {
            Errors.setErrors("WebPageSettings / setUserDetails " + ex.toString());
        }
    }
    
    public static void setCollectralKey (HttpServletRequest request) {
        try {
            String serverkey = request.getParameter("skey");
            String query = "DELETE FROM `" +Constants.db_database + "`.`conf` WHERE TYPE = 'SERVERKEY'";
            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
            stmt.execute();
            stmt.close();
            query = "INSERT INTO `" +Constants.db_database + "`.`conf` (TYPE, VALUE) VALUES ('SERVERKEY', ? )";
            stmt = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, serverkey);
            stmt.execute();
            stmt.close();
            Constants.conf_SERVERKEY = serverkey ;
        } catch (Exception ex) {
            Errors.setErrors("WebPageSettings / setCollectralKey" + ex.toString());
        }
    }
    
    public static void setCompanyName (HttpServletRequest request) {
        
        try {
            String companyname  = request.getParameter("company_name");
            String query = "DELETE FROM `" +Constants.db_database + "`.`conf` WHERE TYPE = 'COMPANYNAME'";
            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
            stmt.execute();
            stmt.close();
            query = "INSERT INTO `" +Constants.db_database + "`.`conf` (TYPE, VALUE) VALUES ('COMPANYNAME', ? )";
            stmt = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, companyname);
            stmt.execute();
            stmt.close();
            Constants.conf_COMPANY = companyname ;
        } catch (Exception ex) {
            Errors.setErrors("WebPageSettings / setCompanyName" + ex.toString());
        }
    }
    
    
    
    
    
    
    
    
    
}
