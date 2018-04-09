<%@page import="errors.Errors"%>
<%@page import="login.LoginAction"%>
<%@page import="assets.Constants"%>
<%@page import="initialize.Application"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%

    request.setAttribute(Constants.page_attribute, Constants.page_file);
    
   
          

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

                                 request.setAttribute(Constants.user_attribute, id_key);
                                 %><jsp:include page="/WEB-INF/jsp_files/webback/router.jsp"></jsp:include><%
                              } else {
                                 %><jsp:include page="/WEB-INF/jsp_files/webfront/router.jsp"></jsp:include><%
                              }
                      } else {

                          request.setAttribute(Constants.user_attribute, id_key);
                          %><jsp:include page="/WEB-INF/jsp_files/webback/router.jsp"></jsp:include><%
                      }

                  } else {
                      %><jsp:include page="/WEB-INF/jsp_files/webback/router.jsp"></jsp:include><%
                  }
         
     
  
%>
