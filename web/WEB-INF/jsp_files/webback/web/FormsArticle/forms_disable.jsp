<%@page import="web.WebPageForms"%>
<%
    String contectxt_url_home_user_add_user = request.getContextPath();
    String [] data  = request.getRequestURI().split("=");
    String [] form_value = WebPageForms.getSingleFormValues(Integer.parseInt(data [1].trim()), request);
    String form_name = form_value[0];
%>

<div style="padding: 20px;">
    
<h1>Are you sure you want to delete form</h1>
<hr>
<h3><%=data[1]%> | <%=form_name%></h3>
<hr>
<form action="<%=contectxt_url_home_user_add_user%>/Forms" method="POST"> 
    <input type="hidden" name="formid" value="<%=data[1]%>">
    <input type="hidden" name="posting" value="disable_from">
    <input type="submit" value="YES" class="gitst-button">
    <a href="<%=contectxt_url_home_user_add_user%>/Forms" class="gitst-button">NO</a>
</form>
    
</div>

