package devices;

import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletGetData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
                       
            String device_key = request.getParameter(ClassConstants.gitst_device_keys);
            String [] keys_vals = device_key.split("_");
            String outpute = "0";
            
            if (keys_vals.length > 1) {
                   try {
                      int deviceid = Integer.parseInt(keys_vals[0]);
                      
                      
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
    
    
    
    

}
