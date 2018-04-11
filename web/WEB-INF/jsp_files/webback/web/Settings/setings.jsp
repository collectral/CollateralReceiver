<%@page import="web.WebConstants"%>
<%@page import="assets.Constants"%>
<%@page import="web.WebPageSettings"%>
<%

 String postying_type = request.getParameter("posting");
 
 if (postying_type != null) {
    if (postying_type.equals("userdata")) {
        WebPageSettings.setUserDetails(request);
    }
    if (postying_type.equals("companyname" )) {
       WebPageSettings.setCompanyName(request);
    }
    if (postying_type.equals("skey")) {
        WebPageSettings.setCollectralKey(request);
    }
}
    
String [] userdata = WebPageSettings.getUserDetails ( request);
String action_url = WebConstants.getContextFullURL(request) + "/Settings";

%>

<div style="padding: 20px"> 
    
    <table style="width: 100%">
        <tr>
            <td style="width: 50%; padding: 30px" valign="top">
                 <h1>Access Details</h1> 
            </td>  
            <td style="width: 50%; padding: 30px" valign="top">
                 <h1>Collectral KEY</h1> 
            </td>
        </tr>
        
        <tr>
            <td style="width: 50%; padding: 30px" valign="top">
                <form action="" method="POST">
                    <input type="text" name="login"        placeholder="Login Details"  value="<%=userdata[0]%>">
                    <hr>
                    <input type="password" name="password" placeholder="Passowrd" >
                    <hr>
                    <input type="email" name="email"       placeholder="Email" value="<%=userdata[2]%>">
                    <hr>
                    <input type="hidden" name="posting"    value="userdata">
                    <input class="gitst-button" type="submit" value="Save">
                </form>
            </td>  
            <td style="width: 50%; padding: 30px" valign="top">
                <form action="" method="POST">
                    <input type="text" name="skey"  placeholder="Server Key" value="<%=Constants.conf_SERVERKEY%>">
                    <hr>
                    <input type="hidden" name="posting"  value="skey">
                    <input class="gitst-button" type="submit" value="Save">
                </form>
            </td>
            
        </tr>
        <tr>
            <td style="width: 50%; padding: 30px" valign="top">
                 
            </td>  
            <td style="width: 50%; padding: 30px" valign="top">
                <h1>Company Details</h1> 
            </td>
        </tr>
         <tr>
            <td style="width: 50%; padding: 30px" valign="top">
             
            </td>  
            <td style="width: 50%; padding: 30px" valign="top">
                <form action="" method="POST">
                    <input type="text" name="company_name" placeholder="Company Name" value="<%=Constants.conf_COMPANY%>">
                    <hr>
                    <input type="hidden" name="posting"    value="companyname">
                    <input class="gitst-button" type="submit" value="Save">
                </form>
            </td>
        </tr>
        
    </table>

</div>
