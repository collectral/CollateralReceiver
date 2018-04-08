<%@page import="assets.Constants"%>
<%@page import="devices.ClassConstants"%>
<%@page import="web.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>
<%
    
  Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);    
  int adminid = 0;
  
  try {
      adminid = Integer.parseInt(id_key[0].toString());
  } catch  (Exception ex) {
      Errors.setErrors(" device_list.jsp " + ex.toString());
  }
  
 
   
  int group_id = 0;
    
  String url_group =  request.getRequestURL().toString();
  try {
        String[] str = url_group.split("=");
        group_id = Integer.parseInt( str [1].trim());
  } catch (Exception ex) {
        Errors.setErrors("JSP / group_edit " + ex.toString());
  }
   
   String group_name = WebPageDevices.getDeviceGroupName ( group_id,  request);
   

%>
<div style="padding: 20px">

<h1> <%=group_name%>  </h1> 
<hr>
<h2>Device List</h2>

<table style="width: 100%"> 
    <tr>
        <td>
            <b>ID</b>
        </td>
        <td>
            <b>DESCRIPTION</b>
        </td>
        <td>
            <b>STATUS</b>
        </td>
        <td>
            <b>ACTION</b>
        </td>
    </tr>
    
    
    <%
        ArrayList objDevices = WebPageDevices .getDeviceList(adminid ,group_id,  request);
        for (int i = 0 ; i <objDevices.size(); i++) {
            Object [] obj = (Object [])objDevices.get(i);
    %>  
            <tr>
                <td style="width: 100px">
                    <%=obj[0]%>
                </td>
                <td>
                    <%=obj[1]%>
                </td>
                <td>
                   <%
                       if (obj[2].toString().length() == 7) {
                           String reg_keys = obj[0] +  "_" + obj[2];
                   %>
                   <b style="color: red"> <%=reg_keys%> </b>
                   <%
                       } else {
                   %>
                           REGISTRED
                   <%
                       }
                   %>
                   
                </td>
                <td style="width: 100px">
                    <a href="">DELETE</a> / <a  href=""> EDIT </a>
                </td>
            </tr> 
    <%
        }
    %>
    
    
</table>   

 <hr style="visibility: hidden">   
 <hr style="visibility: hidden">   
 <hr style="visibility: hidden">   
 <hr style="visibility: hidden">   
 <hr style="visibility: hidden">   
</div>
