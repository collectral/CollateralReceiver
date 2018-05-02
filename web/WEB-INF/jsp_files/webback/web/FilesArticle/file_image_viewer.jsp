<%@page import="web.WebPageFiles"%>
<%@page import="java.util.ArrayList"%>
<%

String [] uriSplit = request.getRequestURI().split("/");

int fieldid = Integer.parseInt( uriSplit[uriSplit.length - 2]);
int fileid  = Integer.parseInt(  uriSplit[uriSplit.length - 1]);

ArrayList imgArray = WebPageFiles.getImages(fieldid, fileid);


%>
<div style="padding: 20px;"> 

<table style="width: 100%">
    <%
        for (int i = 0; i < imgArray.size() ; i++) {
            Object [] imjObj = (Object [])imgArray.get(i);
    %>
            <tr>
                <td>
                    <%=imjObj[0]%>
                </td>    
                    
                <td>
                     <b><%=imjObj[1]%></b>
                </td>
                <td>
                    <img style='display:block; height:100px;' src='data:image/png;base64, <%=imjObj[2]%>' />
                </td>    
               
               
            </tr>
    
    <%
        }
    %>
    
</table>

</div>


<%

%>