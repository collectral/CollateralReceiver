package devices;

import assets.Constants;
import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSyncReg extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            String device_key = request.getParameter(ClassConstants.gitst_device_keys);  ///Example device database id _ device registration key _ devise serial number or unique id
            String respond = "0";
            
            try {
                if (device_key  != null) {
                        String [] device_request =  device_key.split("_");;
                        Object[] device_vals = getDeviceObject (device_key);
                        
                        if (device_vals != null) {
                           int deviceid = Integer.parseInt(device_vals[0].toString());
                           String accesskey = setAcccessKey ( deviceid , device_request[3]);
                           respond = deviceid  + "__" +  accesskey  + "__" + device_vals[2] ;
                        }
                }
            } catch (Exception ex) {
                 Errors.setErrors ("ServletSyncReg / processRequest " + ex.toString());
            }
            out.println(respond);
        }
    }
    
    
    private static Object[] getDeviceObject (String  device_requests) {
        
        Object[] result  = null;

        try {

            String[] splitted = device_requests.split("_");

            int id1 = Integer.parseInt(splitted[0]);
            int id2 = Integer.parseInt(splitted[1]);
            int id3 = Integer.parseInt(splitted[2]);
            String query = "SELECT * FROM  `" +  Constants.db_database  
                    +  "`.`devices` WHERE DKEY = '" +id1 + "_" + id2 + "_" + id3 + "'  LIMIT 1 " ;

            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
            //stmt.setString(1, device_requests);
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) { 
                result    = new Object[3]; 
                result[0] = rs.getInt("ID"); 
                result[1] = rs.getInt("ADMINID"); 
                result[2] = Constants.conf_COMPANY; 
            }

            stmt.close();
            rs.close();
        } catch (Exception ex ) {
            Errors.setErrors("ClassAccess / isAccessAllowed " + ex.toString());
        }  
       
        return result;
    }
    
     
    
    
    private static String setAcccessKey ( int deviceid  , String deviceuid ) {
         
        String unique_key   = null;
        try {
            unique_key = UUID.randomUUID().toString().replaceAll("-", "");
            String query = "UPDATE `" + Constants.db_database +  "`.`devices` SET DKEY = ? , DID = ? WHERE ID = ?";
            PreparedStatement stmt = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, unique_key);
            stmt.setString(2, deviceuid);
            stmt.setInt(3, deviceid);
            stmt.execute();
        } catch (Exception ex) {
            Errors.setErrors ("ServletSyncReg / setAcccessKey " + ex.toString());
        }
        
        return unique_key;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    } 

}
