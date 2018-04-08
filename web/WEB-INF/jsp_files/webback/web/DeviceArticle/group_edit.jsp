<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>
<%
    String contectxt_url_home_user_add_group =  request.getContextPath();        
    int group_id = 0;
    
    String url_group =  request.getRequestURL().toString();
    try {
        String[] str = url_group.split("=");
        group_id = Integer.parseInt( str [1].trim());
    } catch (Exception ex) {
        Errors.setErrors("JSP / group_edit " + ex.toString());
    }
    
    
if (group_id > 0) {
    
    String grou_name =  WebPageDevices.getDeviceGroupName(group_id, request);

%>

<div style="padding: 40px;"> 
    <form action="<%=contectxt_url_home_user_add_group%>/Devices" method="POST">
        <input type="text"     name="groupname"  value="<%=grou_name%>" required><hr style="visibility: hidden">
        <input type="hidden"   name="posting"   value="edit_group">
        <input type="hidden"   name="groupid"   value="<%=group_id%>">
        <input type="submit"   class="gitst-button" value="SAVE" style="width: 95%"><br>
    </form>   
</div>

        
<%
}

%>