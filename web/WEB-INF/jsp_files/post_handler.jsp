<%@page import="initialize.Application"%>
<%@page import="login.Registration"%>
<%

String action_type = request.getParameter("posting");
 

if (action_type != null) {

    if (action_type.equals("dbregister")) {
          Application.writeConfigFile (request);
    }

    if (action_type.equals("dbconfig")) {
            
        
    }


    if (action_type.equals("registration")) {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            Registration.register(email,  fullname);

    }

}






%>




