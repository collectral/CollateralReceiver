package devices;

import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Connect extends HttpServlet {

    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            String respond = "0";            
            try {
                  
                  String key = Security.getKey(request);
                  
                  if (key != null) {
                        String posting = request.getParameter(ClassConstants.posting);
                        HashMap data = Security.getHashDataDecripted(key, request);

                        if (data != null) {
                             if (posting.equals(ClassConstants.posting_get_data)) {
                                 respond = GetData.getResponce(request);
                             }

                             if (posting.equals(ClassConstants.posting_post_data)) {
                                 respond = PostData.getResponce(request);
                             }

                             if (posting.equals(ClassConstants.posting_get_form)) {
                                 respond = GetForm.getResponce(request);
                             }

                             if (posting.equals(ClassConstants.posting_get_forms)) {
                                 respond = GetForms.getResponce(request);
                             }

                             if (posting.equals(ClassConstants.posting_registration)) {
                                 respond = Registration.getResponce(request);
                             }

                             respond = Security.getEncriptedResponce(respond, key);
                        }
                  }
                
                 
            } catch (Exception ex) {
                Errors.setErrors("Connect / processRequest " + ex.toString());
            }
            out.print(respond);
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
