<%@page import="initialize.Application"%>
<%@page import="login.Registration"%>
<%

String action_type = request.getParameter("posting");

if (action_type != null) {
    if (action_type.equals("dbregister")) {
        Application.writeConfigFile (request);
    }
    if (action_type.equals("dbconfig")) {
         Application.setDatabaseValues(request);
    }
    if (action_type.equals("adminregister")) {
            Registration.registeradmin(request);
    }
}

%>




