<%@page import="initialize.Application"%>
<%@page import="assets.Constants"%>
<%@page import="errors.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        %><jsp:include page="/WEB-INF/jsp_files/post_handler.jsp"></jsp:include><%
        
        if (Constants.dbConnection == null) {
            Application.reconnectDatabase();
        }   
        
        if (Constants.dbConnection == null) {
                    %><jsp:include page="/WEB-INF/jsp_files/webfront/dbregister.jsp"></jsp:include><%
        } else {
            if (Constants.dbConnection.isValid(3)) {
                    %><jsp:include page="/WEB-INF/jsp_files/index_init.jsp"></jsp:include><%
            } else {
                if (Constants.dbConnection.isValid(3)) {
                        %><jsp:include page="/WEB-INF/jsp_files/index_init.jsp"></jsp:include><%
                } else {
                    %><jsp:include page="/WEB-INF/jsp_files/webfront/dbregister.jsp"></jsp:include><%
                }
            }
        }    
%>
