package devices;

import assets.Constants;
import com.google.gson.Gson;
import errors.Errors;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

public class GetForm {
    
    private static Gson gson = new Gson ();
    
    public static String getResponce (Object[] deviceKeys, HashMap data) {
        String result  = "0";
        
        HashMap jsonForms = null;
        try {
                       
            // int deviceid = Integer.parseInt(deviceKeys[1].toString());
            // int userid =  Integer.parseInt(deviceKeys[3].toString());
            // String forms_list = data.get(ClassConstants.gitst_device_forms).toString();
            
            String forms_list = GetForms.getResponce(deviceKeys);
            
            System.out.println(forms_list);
            
            String [] formIds = forms_list.split(",");
            
            jsonForms = new HashMap ();
            
            for (int i = 0 ; i < formIds.length ; i++) {
                int formid = Integer.parseInt(formIds[i].trim());
                if (formid > 0) {
                    jsonForms.put(formid, getSingleForm (formid))  ;
                }
            }
            
            jsonForms.put("INFO", getFormsInfo(forms_list));

        } catch (Exception ex) {
            Errors.setErrors("ServletSingleForm / processRequest " + ex.toString());
        }
            
        if (jsonForms != null) {
            result = gson.toJson(jsonForms);
        }
      
        return result;
    }
    
    private static HashMap getFormsInfo (String fids) {
          HashMap result = new HashMap ();
          
          ResultSet rs = null;
          Statement stmt = null;
          try {
           stmt = Constants.dbConnection.createStatement();
           String query = "SELECT * FROM `" + Constants.db_database +"`.`forms` "
                   + " WHERE ENABLED = 1 AND ID IN  (" + fids + ")";
           
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
