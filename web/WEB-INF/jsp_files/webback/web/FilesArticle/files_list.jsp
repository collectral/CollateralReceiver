<%@page import="com.google.gson.JsonObject"%>
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
            <th style="text-align: left">DEVICES</th>
            <th style="text-align: left">FORMS</th>
            <th style="text-align: left; width: 200px">DATE</th>
            <th style="text-align: left; width: 100px">ACTION</th>
        </tr>
<%

for (int i =0 ; i <files.size() ; i++) {
    
    Object[] file = (Object[])files.get(i);
    Object [] obj =  WebPageFiles.getFileJson(Integer.parseInt(file[0].toString()));
    JsonObject dataHash = (JsonObject)obj[2];
    int formid = Integer.parseInt(obj[1].toString()); 
    
    String nameDescription =  WebPageFiles.getFormNameAndDisplay (formid, dataHash);

%>

        <tr>
            <td><%=file[0]%></td>
            <td><%=file[1]%></td>
            <td><%=nameDescription%></td>
            <td><%=file[3]%></td>
            <td>
               
               <a href="<%=file_context_url%>/Files/View=<%=file[0]%>">View</a> 
            </td>
            
        </tr>

<%
}
%>

    </table>
</div>
