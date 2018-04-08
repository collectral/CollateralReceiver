<%@page import="assets.Constants"%>
<%@page import="assets.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String contectxt_url_home_front = request.getContextPath();
        
    

    


%>

<!DOCTYPE html>
<html dir="ltr" lang="en-US">
<head>
    <meta charset="utf-8">
    <title>Home</title>
    <meta name="viewport" content="initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no, width = device-width">
    <link rel="stylesheet" href="<%=contectxt_url_home_front%>/assets/front/style.css" media="screen">
    <link rel="stylesheet" href="<%=contectxt_url_home_front%>/assets/front/style.responsive.css" media="all">
    <script src="<%=contectxt_url_home_front%>/assets/front/jquery.js"></script>
    <script src="<%=contectxt_url_home_front%>/assets/front/script.js"></script>
    <script src="<%=contectxt_url_home_front%>/assets/front/script.responsive.js"></script>
    <meta name="description" content="Description">
    <meta name="keywords" content="Keywords">
</head>

<body>
<div id="gitst-main">
    <div class="gitst-sheet clearfix">
        <div class="gitst-layout-wrapper">
            <div class="gitst-content-layout">
                <div class="gitst-content-layout-row">
                    <div class="gitst-layout-cell gitst-content">
                        <article class="gitst-post gitst-article">
                         
                            <%
                              if (Constants.serverKey == null) {
                            %>
                              <jsp:include page="/WEB-INF/jsp_files/webfront/registration.jsp"></jsp:include>                        
                            <%
                                } else {
                            %>
                               <jsp:include page="/WEB-INF/jsp_files/webfront/login.jsp"></jsp:include>   
                            <%
                              }
                            %>
                           
                        </article>
                    </div>
                </div>
            </div>
        </div>
        
        <footer class="gitst-footer"> 
            Collectral : Free Software Build For Data Collection From Mobile Devices
        </footer>

    </div>
</div>
</body>

</html>