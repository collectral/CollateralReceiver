<%@page import="web.WebConstants"%>
<%@page import="errors.Errors"%>
<%@page import="web.WebPageDevices"%>
<%

String token = request.getParameter("posting");
if (token != null) {
    
    int id  = 0;
    if (token.equals("new_group")) {
        String  groupname  = request.getParameter("groupname");
        if (groupname != null && groupname.length() > 0 ) {
            id =  WebPageDevices.insertGroup(groupname, request) ;
        }
    }
    
    if (token.equals("new_device")) {
        WebPageDevices.generateDevice(request);
    }
    
    if (token.equals("delete_group")) {
        WebPageDevices.deleteSingleGroup(request);
    }
    
    if (token.equals("edit_group")) {
        WebPageDevices.updateGroup (request);
    }
    
    if (token.equals("delete_device")) {
        WebPageDevices.deleteSingleDevice(request);
    }
    
    if (token.equals("edit_device")) {
        WebPageDevices.editDevice(request);
    }
    
     
    
    
}




%>
