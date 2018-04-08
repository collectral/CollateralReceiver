<%@page import="web.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>

<%
    String contectxt_url_home_user_add_group =  request.getContextPath();        
    int device_id = 0;
    
    String url_group =  request.getRequestURL().toString();
    try {
        String[] str = url_group.split("=");
        device_id = Integer.parseInt(str [1].trim());
    } catch (Exception ex) {
        Errors.setErrors("JSP / group_edit " + ex.toString());
    }
    
if (device_id > 0) {
    
    
    String deviceDescription =  WebPageDevices.diviceDescription (device_id, request);
    ArrayList allGroups = WebPageDevices.getAllGroup(request);
    int gid =  WebPageDevices.getDeviceGroupID (device_id, request);
%>

<div style="padding: 20px"> 
    
    <hr>
    <form action="<%=request.getContextPath()%>/Devices" method="POST"> 
        <input type="hidden" name="posting" value="edit_device">
        <input type="hidden" name="deviceid" value="<%=device_id%>">
        <input type="text" name="description"  value="<%=deviceDescription%>" placeholder="Add Device Description">
        <hr>
        <select name="groupid"  style="font-size: 16px"> 
            <option value="0">Select Group</option>
            
            <%  
                for (int jj = 0 ; jj < allGroups.size() ; jj++) {
                      
                   Object [] objGroup = (Object [])allGroups.get(jj);
                    
                   
                    if ((gid+"").equals(objGroup[0].toString().trim())) {
                         %>
                            <option value="<%=objGroup[0]%>" selected><%=objGroup[1]%></option>
                         <%
                    } else {
                        %>
                            <option value="<%=objGroup[0]%>"><%=objGroup[1]%></option>
                        <%
                   }
                }
            %>
            
        </select>
        <hr>
        <input type="submit"  class="gitst-button" value="SAVE CHANGES">
    </form>
    
</div>

<%
}
%>