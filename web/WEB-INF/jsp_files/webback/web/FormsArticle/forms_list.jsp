<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageForms"%>
<%
     String contectxt_url_home_user_add_user = request.getContextPath();
     ArrayList forms_names = WebPageForms.getAllForms(0, request);
%>

<div style="padding: 20px">
        <table style="width: 100%"> 
            <tr>
                <th style="width: 30px; text-align: left">ID<hr></th>
                <th style="text-align: left">Form Name <hr></th>
                <th style="width: 140px; text-align: left">Date<hr></th>
                <th style="width: 70px; text-align: left">Action<hr></th>
            <tr>
              
             <%
                for (int fi = 0 ; fi < forms_names.size() ; fi++) {
                        Object[] obj_forms =(Object[])forms_names.get(fi);
             %>
                        <tr>
                            <td style="width: 30px"><%=obj_forms[0]%></td>
                            <td><%=obj_forms[1]%></td>
                            <td style="width: 140px"><%=obj_forms[2]%></td>
                            <td style="width: 70px"> <a href="<%=contectxt_url_home_user_add_user%>/Forms/Disable=<%=obj_forms[0]%>">Delete</a> /  <a href="<%=contectxt_url_home_user_add_user%>/Forms/Edit=<%=obj_forms[0]%>">Edit</a>  </td>
                        </tr>               
             <%
                }
             %>   
             
        </table>
</div>