<%@page import="login.LogOutAction"%>
<%@page import="web.WebConstants"%>
<%@page import="assets.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    String contectxt_url_home_back = request.getContextPath();
    
    
%>
<!DOCTYPE html>
<html dir="ltr" lang="en-US">
    <head>
        <meta charset="utf-8">
        <title>New Page</title>
        <meta name="viewport" content="initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no, width = device-width">
        <link rel="stylesheet" href="<%=contectxt_url_home_back%>/assets/back/style.css" media="screen">
        <link rel="stylesheet" href="<%=contectxt_url_home_back%>/assets/back/style.responsive.css" media="all">
        <script src="<%=contectxt_url_home_back%>/assets/back/jquery.js"></script>
        <script src="<%=contectxt_url_home_back%>/assets/back/script.js"></script>
        <script src="<%=contectxt_url_home_back%>/assets/back/script.responsive.js"></script>
        
        <script src="<%=contectxt_url_home_back%>/assets/JQ/jquery-ui.min.js"></script>
        <link rel="stylesheet" href="<%=contectxt_url_home_back%>/assets/JQ/jquery-ui.min.css" media="all">
        
        
        <style>.gitst-content .gitst-postcontent-0 .layout-item-0 { color: #2B2B2B; background: #FCFCFC; padding: 0px;  }
        .ie7 .gitst-post .gitst-layout-cell {border:none !important; padding:0 !important; }
        .ie6 .gitst-post .gitst-layout-cell {border:none !important; padding:0 !important; }

        </style>
    </head>
<body>
<div id="gitst-main">
 
<div class="gitst-sheet clearfix">
        <nav class="gitst-nav">
                <ul class="gitst-hmenu">
                    <li><a href="<%=contectxt_url_home_back%>/Files">Files</a></li>  
                    <li><a href="<%=contectxt_url_home_back%>/Forms">Forms</a></li>
                    <li><a href="<%=contectxt_url_home_back%>/Devices">Devices</a></li>  
                    <li><a href="<%=contectxt_url_home_back%>/Settings">Settings</a></li>  
                </ul> 
               
                <form action="" method="POST" style="float:  right; position:  relative; top: 7px; ">
                   <input type="hidden" name="posting" value="logout">
                   <input  class="gitst-button" type="submit" value="Logout" style="font-size: 14px;  border: 1px solid red;">  
                </form>    
                
        </nav>
<%
        int back_page_id = Integer.parseInt( request.getAttribute(Constants.page_attribute).toString());
                                                            switch (back_page_id) {
                                                                  case Constants.page_file:
                                                                            %>
                                                                             <jsp:include page="/WEB-INF/jsp_files/webback/web/files.jsp"></jsp:include>
                                                                            <%
                                                                      break;
                                                                  case Constants.page_form: 
                                                                            %>
                                                                             <jsp:include page="/WEB-INF/jsp_files/webback/web/forms.jsp"></jsp:include>
                                                                           <%
                                                                       break;
                                                                  case Constants.page_devices: 
                                                                       %>
                                                                        <jsp:include page="/WEB-INF/jsp_files/webback/web/devices.jsp"></jsp:include>
                                                                       <%
                                                                      break;
                                                                  case Constants.page_settings: 
                                                                       %>
                                                                        <jsp:include page="/WEB-INF/jsp_files/webback/web/Settings/setings.jsp"></jsp:include>
                                                                       <%
                                                                      break;
                                                            }                             
%>
                                    <footer class="gitst-footer">
                                         <p>Copyright © 2017-2018. All Rights Reserved.</p>
                                         <p><br></p>
                                    </footer>
    </div>
</div>

</body>
</html>

 

