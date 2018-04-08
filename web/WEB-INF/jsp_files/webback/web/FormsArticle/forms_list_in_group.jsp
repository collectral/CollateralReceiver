<%@page import="errors.Errors"%>
<%@page import="web.WebPageForms"%>
<%@page import="java.util.ArrayList"%>
<%
    
    String[] ids = request.getRequestURL().toString().split("Group=");   
    
    int forms_group_id = 0;
    try {
      forms_group_id = Integer.parseInt(ids[1].trim()); 
    } catch (Exception ex) {
        Errors.setErrors ("JSP forms_list_in_group / " + ex.toString());
    }
     ArrayList forms_names = WebPageForms.getAllForms(forms_group_id, request);
     
%>

<div style="padding: 20px">
    
     
        <table style="width: 100%"> 
            <tr>
                <th style="width: 100px; text-align: left">ID<hr></th>
                <th style="text-align: left">Form Name <hr></th>
                <th style="width: 140px; text-align: left">Date<hr></th>
            <tr>
              
             <%
               for (int fi = 0 ; fi < forms_names.size() ; fi++) {
                 Object[] obj_forms =(Object[])forms_names.get(fi);
             %>
                <tr>
                <td style="width: 100px"><%=obj_forms[0]%></td>
                <td><%=obj_forms[1]%></td>
                <td style="width: 140px"><%=obj_forms[2]%></td>
               </tr>
                
             <%
             
             }
             
             %>   
             
        </table>
        
    
</div>