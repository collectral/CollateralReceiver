<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageFiles"%>
<%
  String file_context_url =   request.getContextPath();
  ArrayList files = WebPageFiles.getFiles(50);
%>


<div style="padding: 20px;"> 

    <table style="width: 100%">
        <tr>
            <th style="text-align: left; width: 30px">ID</th>
            <th style="text-align: left">USER</th>
            <th style="text-align: left">FORM</th>
            <th style="text-align: left; width: 100px">DATE</th>
            <th style="text-align: left; width: 100px">ACTION</th>
        </tr>
<%

for (int i =0 ; i <files.size() ; i++) {
    Object[] file = (Object[])files .get(i);
%>

        <tr>
            <td><%=file[0]%></td>
            <td><%=file[1]%></td>
            <td><%=file[2]%></td>
            <td><%=file[3]%></td>
            <td>
               <!-- <a href="<%=file_context_url%>/Files/Edit=<%=file[0]%>">Edit</a> | -->
               <a href="<%=file_context_url%>/Files/View=<%=file[0]%>">View</a> 
            </td>
            
        </tr>

<%
}
%>

    </table>
</div>
