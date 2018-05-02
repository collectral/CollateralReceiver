package devices;

import assets.Constants;
import assets.Encription;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import errors.Errors;
import initialize.Application;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Connect extends HttpServlet {
        
    private static Gson gson = new Gson ();
        
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        try (PrintWriter out = response.getWriter()) {
            String respond = "0";            
            try {
                
                  validateDbConnection();
                   
                  Object[] key = Security.getKey(request);
                  
                  if (key != null) {
                         
                        String posting = request.getParameter(ClassConstants.posting);
                        String getCleanJson = Security.getHashDataDecripted(key[0].toString(), request) ;
                        HashMap data =  gson.fromJson(getCleanJson, HashMap.class);;
                        JsonObject data_json = new JsonParser().parse(getCleanJson).getAsJsonObject();
                        
                        if (data != null) {
                             if (posting.equals(ClassConstants.posting_get_data)) {
                                 respond = GetData.getResponce(request);
                             }

                             if (posting.equals(ClassConstants.posting_post_data)) {
                                 respond = PostData.getResponce(key, data_json);
                             }

                             if (posting.equals(ClassConstants.posting_get_form)) {
                                 respond = GetForm.getResponce(key ,data );
                             }
                             
                             if (posting.equals(ClassConstants.posting_get_forms)) {
                                 respond = GetForms.getResponce(key);
                             }

                             if (posting.equals(ClassConstants.posting_registration)) {
                                 respond = Registration.getResponce(key, data);
                             }
                             
                             respond = Encription.textMixer (respond);
                             respond = Encription.getEncriptedString(respond, key[0].toString().trim());
                        }
                  }
         
            } catch (Exception ex) {
                Errors.setErrors("Connect / processRequest " + ex.toString());
            }
            out.print(respond);
        }
    }
    
    private static void validateDbConnection() {
        try {
            int state = 0;
            if (Constants.dbConnection == null) {
               state = 1;  
            }  
            if (!Constants.dbConnection.isValid(3)) {
               state = 2;  
            } 
            if (state > 0) {
                Application.connectDatabase();
            }
        } catch (Exception ex) {
            Errors.setErrors("Connect / validateDbConnection " + ex.toString());
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
