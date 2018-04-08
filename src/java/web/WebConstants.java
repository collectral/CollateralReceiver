package web;

import javax.servlet.http.HttpServletRequest;

public class WebConstants {
    
    public static String sectionident_post   = "sectionident";
    public static String fieldident_post     = "fieldident";
    public static String form_name_post      = "form_name";
    public static String form_name_url       = "form_url";
    public static String sectionname_post    = "sectionname";
    public static String fieldName_post      = "fieldName";
    public static String fieldDefault_post   = "fieldDefault";
    public static String field_type_post     = "field_type";
    public static String mandatory_post      = "mandatory";
    public static String visible_post        = "visible";
    public static String assignedgroups_post = "assignedgroups";
       
    
    public static String getContextFullURL (HttpServletRequest httprequest) {
            String result = httprequest.getRequestURL()
                    .substring(0, httprequest.getRequestURL().length() - httprequest.getRequestURI() .length() );
            result = result  +  httprequest.getContextPath();
            return result;
    }
    
    
}
