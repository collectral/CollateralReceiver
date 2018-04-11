<%@page import="assets.Constants"%>
<%@page import="devices.ClassConstants"%>
<%@page import="web.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>
<%

  String contectxt_url_home_user_add_user = request.getContextPath();
  Object[] id_key = (Object []) request.getAttribute(Constants.user_attribute);    
  int adminid = 0;
  
  try {
      adminid = Integer.parseInt(id_key[0].toString());
  } catch  (Exception ex) {
      Errors.setErrors(" device_list.jsp " + ex.toString());
  }
   
  ArrayList objGroups =  WebPageDevices.getAllGroup (request);

%>

<div style="padding: 20px">
<h2>Group List</h2>
<table style="width: 100%"> 
    <tr>
        <td>
            <b>ID</b>
        </td>
        <td>
            <b>NAME</b>
        </td>
        <td>
           <b> ACTIONS</b>
        </td>
    </tr>
    
    
    <%
        for (int i = 0 ; i <objGroups.size(); i++) {
            Object [] obj = (Object [])objGroups.get(i);
    %>  
            <tr>
                <td style="width: 100px">
                    <%=obj[0]%>
                </td>
                <td>
                    <%=obj[1]%>
                </td>
                <td style="width: 100px">
                    <a href="<%=contectxt_url_home_user_add_user%>/Devices/DeleteDeviceGroup=<%=obj[0]%>">DELETE</a> / <a href="<%=contectxt_url_home_user_add_user%>/Devices/EditDeviceGroup=<%=obj[0]%>">EDIT</a> 
                </td>
            </tr> 
    <%
        }
    %>
    
    
</table>   

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
        ArrayList objDevices = WebPageDevices .getDeviceList(adminid , 0 , request);
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
                       String[] spl = obj[2].toString().split("_");
                       if (spl.length == 3) {
                   %>
                       <b style="color: red"><%=obj[2]%> </b>
                   <%
                       } else {
                   %>
                         REGISTRED
                   <%
                       }
                   %>
                   
                </td>
                <td style="width: 100px">
                        <a href="<%=contectxt_url_home_user_add_user%>/Devices/DeleteDevice=<%=obj[0]%>">DELETE</a> / <a href="<%=contectxt_url_home_user_add_user%>/Devices/EditDevice=<%=obj[0]%>">EDIT</a> 
                </td>
            </tr> 
    <%
        }
    %>
    
</table>   

</div>
