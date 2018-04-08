<%
 String context_url_login = request.getContextPath();
%>

<div id="loginform" style="position: absolute; width: 200px; border:  #2B7DB1 solid thick; padding: 30px;  left: 35%; top: 20%">
     
    <form  method="POST" action="<%=context_url_login%>/web" style="width: 200px"> 
        <input name="username" style="height: 7px" type="text" placeholder="Login" style="width: 100%" ><br>
        <input name="password" style="height: 7px" type="password" placeholder="Password" style="width: 100%"><br>
        <input type="submit" style="width: 170px"  name="login" value="Login"  style="width: 80%"><br>
    </form> 
    <a href="#" onclick="registerLogin()">Register</a>
</div>
