package devices;

import assets.Constants;
import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletFormsList extends HttpServlet {
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            
            String device_key = request.getParameter(ClassConstants.gitst_device_keys);
            String [] keys_vals = device_key.split("__");
            String outpute = "0";
            
            if (keys_vals.length > 1) {
                   try {
                      int deviceid = Integer.parseInt(keys_vals[0]);
                      int[] devivedata =  ClassAccess.isAccessDeviceAllowed(deviceid,  keys_vals[1]);
                      int adminid = devivedata[0];
                      
                      if (adminid > 0) {
                          String groupList = getGroupIDsInString (deviceid);
                          outpute = "";
                          ArrayList formsIds =  getFormsArray (groupList, adminid);
                          
                          for (int fil = 0 ; fil <formsIds.size() ; fil++ ) {
                              try {
                                   outpute = outpute +  formsIds.get(fil) + ",";
                              } catch (Exception ex1) {
                                   Errors.setErrors(ex1.toString());
                              }
                          }
                          outpute = outpute.substring(0, outpute.length() - 1);
                      }  
                   } catch (Exception ex) {
                       Errors.setErrors("ServletTemplates / processRequest " + ex.toString());
                   }
            }
            out.println(outpute);
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
            Errors.setErrors ("ServletTemplates / getGroupLists " + ex.toString());
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
            Errors.setErrors ("ServletTemplates / getFormsArray " + ex.toString());
        }   
        
        try {rs.close();}catch (Exception ex){} 
        try {stmt.close();}catch (Exception ex){} 
        
        return result;
    }
    
   
   
    
    
}
