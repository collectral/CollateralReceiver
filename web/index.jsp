<%@page import="login.LogOutAction"%>
<%@page import="login.LoginAction"%>
<%@page import="web.WebConstants"%>
<%@page import="initialize.Application"%>
<%@page import="assets.Constants"%>
<%@page import="errors.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/jsp_files/post_handler.jsp"></jsp:include>
<%
    
        int state = 0;
    
     
        if (Constants.dbConnection == null) {
           state = 1;  
        }  
        
        if (!Constants.dbConnection.isValid(3)) {
           state = 2;  
        } 
        
        if (state > 0) {
            Application.connectDatabase();
        }
%> 


<%

if (state == 0) {
            request.setAttribute(Constants.page_attribute, Constants.page_home);

            Object[] id_key = null;

            Cookie cookie = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                  cookie = cookies[i];
                  if (cookie.getName().equals(Constants.gitst_cookie)) {
                         id_key = LoginAction.getUserKeyFromCookies(cookie.getValue().trim());
                  } 
            }
                        
            if (id_key == null) {

                    if (request.getParameter("login") != null) {
                        String username = request.getParameter("username") ;
                        String password = request.getParameter("password");
                        id_key = LoginAction.getUserKeyFromLoginPass(username, password);
                    }  

                    if (id_key != null) {
                       Cookie new_access_cookie = new Cookie(Constants.gitst_cookie, id_key[1].toString());
                       new_access_cookie.setMaxAge(365*24*60*60);
                       response.addCookie(new_access_cookie);
                       request.setAttribute(Constants.user_attribute, id_key);

                       response.sendRedirect( WebConstants.getContextFullURL (request) + "/Files");

                    } else {
                       %><jsp:include page="/WEB-INF/jsp_files/webfront/router.jsp"></jsp:include><%
                    }
            } else {
                request.setAttribute(Constants.user_attribute, id_key);
                response.sendRedirect( WebConstants.getContextFullURL (request) + "/Forms");
            }

    } else {
        %><jsp:include page="/WEB-INF/jsp_files/webfront/router.jsp"></jsp:include><%
    }
} else {
    %><jsp:include page="/WEB-INF/jsp_files/webfront/dbregister.jsp"></jsp:include><%
}

%>