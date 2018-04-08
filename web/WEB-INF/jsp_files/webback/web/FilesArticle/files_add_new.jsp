<%
    String contectxt_url_home_user_add_user = request.getContextPath();
    // out.println(request.getContextPath());
    // out.println(request.getRequestURI());
    // out.println(request.getRequestURL());
    


%>
<div style="padding: 20px;"> 

File Add

<div style="padding: 40px;"> 
    <form action="<%=contectxt_url_home_user_add_user%>/Users" method="POST">
        <input type="text"     name="fullname"  placeholder="Full Name" required><hr style="visibility: hidden">
        <input type="email"    name="email"     placeholder="Email" required> <hr style="visibility: hidden">
        <input type="password" name="password"  placeholder="Password" required> <hr style="visibility: hidden">
        <input type="hidden"   name="posting"   value="new_user">
        <input type="submit"   class="gitst-button" value="SAVE" style="width: 95%"><br>
    </form>   
</div>

</div>
