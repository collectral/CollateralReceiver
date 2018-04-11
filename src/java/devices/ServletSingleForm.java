package devices;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletSingleForm extends HttpServlet {
    
    private static Gson gson = new Gson ();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (PrintWriter out = response.getWriter()) {
            
            String outpute = "0"; /// Means no changes in Forms
            HashMap jsonForms = null;
            try {
                String device_key = request.getParameter(ClassConstants.gitst_device_keys);
                String [] keys_vals = device_key.split("__");
              
                if (keys_vals.length > 1) {
                           try {
                              int deviceid = Integer.parseInt(keys_vals[0]);
                              int[] userdata =  ClassAccess.isAccessDeviceAllowed(deviceid,  keys_vals[1]);
                              
                              int userid =  userdata[0];
                              if (userid > 0) {
                                    String forms_list = request.getParameter(ClassConstants.gitst_device_forms).trim();
                                    String [] formIds = forms_list.split(",");

                                    jsonForms = new HashMap ();

                                    for (int i = 0 ; i < formIds.length ; i++) {
                                                      int formid = Integer.parseInt(formIds[i].trim());
                                                      if (formid > 0) {
                                                         jsonForms.put(formid, getSingleForm (formid))  ;
                                                      }
                                    }
                                    
                                    
                                    
                                    jsonForms.put("INFO", getFormsInfo (forms_list, userdata) )  ;
                              }
                             
                           } catch (Exception ex) {
                               Errors.setErrors("ServletSingleForm  / processRequest " + ex.toString());
                           }
                }
            } catch (Exception ex) {
                    Errors.setErrors("ServletSingleForm / processRequest " + ex.toString());
            }
            
            if (jsonForms != null) {
               outpute = gson.toJson(jsonForms);
            }
            
            out.print(outpute);
            
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
    
    private static HashMap getFormsInfo (String fids, int[] userdata) {
          HashMap result = new HashMap ();
          
          ResultSet rs = null;
          Statement stmt = null;
          try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms` "
                   + " WHERE ADMINID = " + userdata[0] + " AND  ENABLED = 1 AND ID IN  (" + fids + ")";
           
           rs = stmt.executeQuery(query);
           
           while (rs.next()) {   
                 HashMap singleFormInfo  = new HashMap();
                 singleFormInfo.put("NAME",rs.getString("NAME")) ;
                 singleFormInfo.put("TS",  rs.getInt("UDATE")) ;
                 singleFormInfo.put("URL",  rs.getString("URL")) ;
                 result.put(rs.getInt("ID"), singleFormInfo);
           }
           
        } catch (Exception ex) {
             Errors.setErrors ("ServletSingleForm / getFormName " + ex.toString());
        }   
        
        try {if ( rs != null ){ rs.close(); }}catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
        
        return result;
        
    }
    
    /**
     * Generate single Form 
     * @param id
     * @param name
     * @formidstoget ID of forms which should be received 
     * @return 
     */
    private static HashMap getSingleForm (int fid) {
        HashMap singleFormResult  = new HashMap();
        
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms_sections` WHERE FID = '" + fid + "'";
           rs = stmt.executeQuery(query);
           
           HashMap sectionNames = new HashMap ();
           
           while (rs.next()) {   
               int section_number = rs.getInt("FP");
               HashMap section =  getSection (fid, rs.getInt("ID"), section_number); 
               
               if (section != null ) {
                   singleFormResult.put(rs.getInt("ID") + "", section);
                   sectionNames.put(rs.getInt("ID") + "", rs.getString("NAME"));
               }
               
               
           }
           
           
           if (singleFormResult.size() > 0) {
              singleFormResult.put("NAMES", sectionNames);
           }
           
           
        } catch (Exception ex) {
           Errors.setErrors ("ServletTemplates / getSingleForm /" + ex.toString());
        }   
        
        try {if ( rs != null ) {rs.close();} }catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
       
        return singleFormResult;
        
    } 
    
    private static HashMap getSection (int fid, int sid, int section_number) {
        
        HashMap singleSection  = new HashMap();
        
        ResultSet rs = null;
        Statement stmt = null;
        
        try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms_fields` WHERE FID = '" + fid + "' AND SID = '" + sid +  "'";
           rs = stmt.executeQuery(query);
           
           while (rs.next()) {   
                HashMap singleField = new HashMap();
                singleField.put("ID", rs.getInt("ID"));
                singleField.put("NAME", rs.getString("NAME"));
                singleField.put("DVAL", rs.getString("DVAL"));
                singleField.put("TYPE", rs.getInt("TYPE"));
                singleField.put("MAND", rs.getInt("MAND"));
                singleField.put("DISPL", rs.getInt("DISPL"));
                
                singleSection.put(rs.getInt("ID") + "", singleField);
               
           }
           
           if (singleSection.isEmpty()) {
               singleSection  = null;
           } else {
              singleSection.put("SN", section_number + "");
           }
           
        } catch (Exception ex) {
           Errors.setErrors ("ServletTemplates / getSingleForm /" + ex.toString());
        }   
        
        try {if ( rs != null ){ rs.close(); }}catch (Exception ex){} 
        try {if ( stmt != null) {stmt.close();}} catch (Exception ex){}  
       
        return singleSection;
        
    }
    
    
    
    
    
}
