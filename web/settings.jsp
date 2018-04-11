<%@page import="login.LogOutAction"%>
<%@page import="web.WebConstants"%>
<%@page import="initialize.Application"%>
<%@page import="assets.Constants"%>
<%@page import="errors.Errors"%>
<%@page import="login.LoginAction"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    
    if (Constants.dbConnection == null) {
       response.sendRedirect( WebConstants.getContextFullURL (request));
    }   
    
    if (!Constants.dbConnection.isValid(3)) {
       response.sendRedirect( WebConstants.getContextFullURL (request));
    } 
    
    
    
    request.setAttribute(Constants.page_attribute, Constants.page_settings);
    
    Object[] id_key = null;
    Cookie cookie = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {

        for (int i = 0; i < cookies.length; i++) {
            cookie = cookies[i];
            if (cookie.getName().equals(Constants.gitst_cookie)) {
                id_key = LoginAction.getUserKeyFromCookies(cookie.getValue().trim());
                break;
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
                } 
        } 
    } 
        
    if (id_key != null) {
        String logout = request.getParameter("posting") ;
        if (logout!= null) {
            if (logout.equals("logout")) {
                LogOutAction.deleteKey(id_key);
                response.sendRedirect( WebConstants.getContextFullURL (request));
            } 
        }

        request.setAttribute(Constants.user_attribute, id_key);
        %><jsp:include page="/WEB-INF/jsp_files/webback/router.jsp"></jsp:include><%
    } else {
        response.sendRedirect( WebConstants.getContextFullURL (request));
    }


%>
