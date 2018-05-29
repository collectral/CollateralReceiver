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
                    <img  onclick="show_image(this)" style='display:block; height:100px;' src='data:image/png;base64, <%=imjObj[2]%>' />
                  
                    
                </td>    
               
               
            </tr>
    
    <%
        }
    %>
    
</table>

</div>
    

    

<div  style="height: 0px; visibility: hidden">    
    <div id="show_image" title="Image Full Size" >
       <img  id="dialogimage"  style='display:block; height: 600px' src='' />
    </div>
    <script>
        
         
       

        
       function show_image(object) {
            var image64 = object.getAttribute("src");
            $( "#dialogimage" ).attr("src" , image64);
            
            
             $( function() {
                $( "#show_image" ).dialog({
                  resizable: false,
                  height: 600,
                  width: "auto",
                  modal: true,
                  buttons: {
                    "CLOSE": function() {
                      $( this ).dialog( "close" );
                    }
                  }
                });
              } );
            
            
            
       }
    </script>
</div>

