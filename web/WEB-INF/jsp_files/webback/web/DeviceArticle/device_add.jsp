<%@page import="web.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageDevices"%>
<%@page import="errors.Errors"%>

<%
    ArrayList allGroups = WebPageDevices.getAllGroup (request);
%>

<div style="padding: 20px"> 
    
    <hr>
    <form action="<%=request.getContextPath()%>/Devices" method="POST"> 
        <input type="hidden" name="posting" value="new_device">
       
        <input type="text" name="description" placeholder="Add Device Description">
        <hr>
        <select name="groupid"  style="font-size: 16px"> 
            <option value="0">Select Group</option>
            
            <%  
                for (int jj = 0 ; jj < allGroups.size() ; jj++) {
                       Object [] objGroup = (Object [])allGroups.get(jj);
            %>
                       <option value="<%=objGroup[0]%>"><%=objGroup[1]%></option>
            <%
                }
            %>
            
        </select>
        <hr>
        <input type="submit"  class="gitst-button" value="GENERATE DEVICE ACCESS">
    </form>
    
</div>
