<%
    String contectxt_url_home_user_add_group =  request.getContextPath();
%>


<div style="padding: 40px;"> 
    <form action="<%=contectxt_url_home_user_add_group%>/Devices" method="POST">
        <input type="text"     name="groupname"  placeholder="Group Name" required><hr style="visibility: hidden">
        <input type="hidden"   name="posting"   value="new_group">
        <input type="submit"   class="gitst-button" value="SAVE" style="width: 95%"><br>
    </form>   
</div>