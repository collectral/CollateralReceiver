<%@page import="errors.Errors"%>
<%@page import="web.WebPageForms"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="web.WebPageDevices"%>
<%

String tokenForms = request.getParameter("posting");

if (tokenForms != null) {
    
    if (tokenForms.equals("new_form")) {
         WebPageForms.setForm( 0 , request);
    }
    
    if (tokenForms.equals("disable_from")) {
        int formid = 0 ;
        try {
            formid = Integer.parseInt(request.getParameter("formid").trim())  ;
        } catch (Exception ex) {
            Errors.setErrors("JSP post_handler " + ex.toString());  
        } 
        if (formid > 0) {
          WebPageForms.deleteForm(formid, request);
        }
    }
    
    if (tokenForms.equals("edit_form")) {
         WebPageForms.editForm(request);
    }
    
    
}




%>
