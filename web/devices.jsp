<%@page import="initialize.Application"%>
<%@page import="assets.Constants"%>
<%@page import="errors.Errors"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
        if (Constants.dbConnection == null) {
            Application.reconnectDatabase();
        }   
        if (Constants.dbConnection == null) {
                    %><jsp:include page="/WEB-INF/jsp_files/webfront/dbregister.jsp"></jsp:include><%
        } else {
            if (Constants.dbConnection.isValid(3)) {
                    %><jsp:include page="/WEB-INF/jsp_files/devices.jsp"></jsp:include><%
            } else {
                if (Constants.dbConnection.isValid(3)) {
                        %><jsp:include page="/WEB-INF/jsp_files/devices.jsp"></jsp:include><%
                } else {
                    %><jsp:include page="/WEB-INF/jsp_files/webfront/dbregister.jsp"></jsp:include><%
                }
            }
        }    
%>