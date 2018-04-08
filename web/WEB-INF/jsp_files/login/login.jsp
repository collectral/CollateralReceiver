<%@page import="assets.Constants"%>
<%
String context_url_login = request.getContextPath();
%>

<div id="loginform">
     
    <form  method="POST" action="<%=context_url_login%>/web"> 
        <input name="username" style="height: 7px" type="text" placeholder="Login"><br>
        <input name="password" style="height: 7px" type="password" placeholder="Password"><br>
        <input type="submit" style="width: 170px"  name="login" value="Login"><br>
    </form> 
    <a href="#" onclick="registerLogin()">Register</a>
</div>

<div id="regform" style="visibility: hidden; height: 0px">
    <form  method="POST" action="<%=context_url_login%>/web"> 
        <input name="fullname" style="height: 7px"  type="text" placeholder="Full Name"><br>
        <input name="email"    style="height: 7px"  type="Text" placeholder="Email"><br>
        
        <input type="submit"   style="width: 170px" name="registration" value="Register"><br>
    </form>
    <a href="#" onclick="registerLogin()">Login</a>
</div>
 
 
<script>
    function registerLogin() {
        var stylevar = 'visibility: hidden; height: 0px';
        if (document.getElementById("loginform").hasAttribute("style")) {
             document.getElementById("loginform").removeAttribute("style");
             document.getElementById("regform").setAttribute("style", stylevar);
        } else {
             document.getElementById("regform").removeAttribute("style");
             document.getElementById("loginform").setAttribute("style", stylevar);
        }
    } 
</script>
