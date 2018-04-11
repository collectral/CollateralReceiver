package devices;

import assets.Constants;
import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletConfirm extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        try (PrintWriter out = response.getWriter()) {
                       
             String device_key = request.getParameter(ClassConstants.gitst_device_keys);
             String [] keys_vals = device_key.split("_");
             String outpute = "0";
             
             if (keys_vals.length > 1) {
                   try {
                      int deviceid = Integer.parseInt(keys_vals[0]);
                      int[] userdata =  ClassAccess.isAccessDeviceAllowed(deviceid,  keys_vals[1]);
                      int userid = userdata[0];
                      if (userid > 0) {
                         update_user_access_date (deviceid);
                      }  
                   } catch (Exception ex) {
                       Errors.setErrors("ServletTemplates / processRequest " + ex.toString());
                   }
             }
             out.println(outpute);
        }
    }
    
    /**
     * Update is cold after Synchronization confirmed to be success 
     * @param deviceid Device Which get updates successfully 
    */
    public static void update_user_access_date (int deviceid) {
        Statement stmt = null;
        try {
           stmt = Constants.dbConnection.createStatement();
           String query  = "UPDATE `"  + Constants.db_database +"`.`devices` SET UDT = CURRENT_TIMESTAMP WHERE ID = " + deviceid;
           stmt.execute(query);
           stmt.close();
        } catch (Exception ex) {
           Errors.setErrors ("ServletConfirm / update_user_access_date" + ex.toString());
        }   
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
