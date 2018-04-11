package devices;

import assets.Constants;
import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSetData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try (PrintWriter out = response.getWriter()) {
               
            String device_key = request.getParameter(ClassConstants.gitst_device_keys);
            String [] keys_vals = device_key.split("_");
            String outpute = "0";
            
            if (keys_vals.length > 1) {
                   try {
                      int deviceid = Integer.parseInt(keys_vals[0]);
                      
                      if (deviceid > 0) {
                          
                          String formid   = request.getParameter(ClassConstants.formid).trim();
                          String fileJson = request.getParameter(ClassConstants.json).trim();
                          // deviceid
                          // userid
                          try {
                            if (setDatabase (deviceid ,Integer.parseInt(formid), fileJson) > 0) {
                                outpute = "1";
                            } 
                          } catch (Exception ex) {
                            Errors.setErrors("ServletSetData / processRequest 1 " + ex.toString());
                          }
                         
                      }  
                   } catch (Exception ex) {
                       Errors.setErrors("ServletSetData / processRequest 2 " + ex.toString());
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
    
    private static int setDatabase (int deviceid, int formid, String fileJson) {
        int result = 0;
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            
            String query = "INSERT INTO `" +  Constants.db_database  +  "`.`data_get` "
                  + " (DEVICEID, FORMID, JSONTEXT) "
                  + " VALUES "
                  + " (?, ?, ?)" ;
            
            stmt  = Constants.dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, deviceid);
            stmt.setInt(2, formid);
            stmt.setString(3,fileJson);
            stmt.execute();
            
            rs = stmt.getGeneratedKeys();
            if (rs.next()){
                result=rs.getInt(1);
            }
            
        } catch (Exception ex) {
            Errors.setErrors("ServletSetData / setDatabase "  + ex.toString());
        }
        
        
        try {stmt.close();} catch (Exception ex) {}
        try {rs.close();} catch (Exception ex) {}
        
        return result;
    }
    
    
    
}
