<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>
<%
    String contectxt_url_home_user_add_group =  request.getContextPath();        
    int device_id = 0;
    
    String url_group =  request.getRequestURL().toString();
    try {
        String[] str = url_group.split("=");
        device_id = Integer.parseInt( str [1].trim());
    } catch (Exception ex) {
        Errors.setErrors("JSP / group_edit " + ex.toString());
    }
    
    
if (device_id > 0) {
    

%>

<div style="padding: 40px;"> 
    <h1>Are You Sure you want to Delete Device ?</h1>
    <hr>
    <form action="<%=contectxt_url_home_user_add_group%>/Devices" method="POST">
        <input type="hidden"   name="posting"   value="delete_device">
        <input type="hidden"   name="deviceid"   value="<%=device_id%>">
        <input type="submit"   class="gitst-button" value="DELETE" style="width: 95%"><br>
    </form>   
</div>
        
<%
}

%>