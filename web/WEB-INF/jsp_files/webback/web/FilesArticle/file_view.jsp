<%@page import="com.google.gson.JsonObject"%>
<%@page import="assets.Constants"%>
<%@page import="web.WebPageForms"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="web.WebPageFiles"%>
<%@page import="errors.Errors"%>
<%
    
int fileid = 0;
  


try {
   String [] uriSplit = request.getRequestURI().split("=");
   fileid = Integer.parseInt(uriSplit [1]);

if (fileid > 0 ) {
   Object [] obj =  WebPageFiles.getFileJson(fileid);
   
   
   if (obj != null) {
       
    JsonObject dataHash = (JsonObject)obj[2];
   
    System.out.println(dataHash .toString());
   
    int formid = Integer.parseInt(obj[1].toString()); 
    
    if (formid > 0) {

             String formName = WebPageFiles.getFormNameAndDisplay (formid, dataHash);
             

      %>

      <div style="padding: 20px">
              <div id="form_content">
                  <input  type="text" style="width: 100%" value="<%=formName%>"  readonly >

                  <hr style="visibility: hidden">
      <%
          ///// Section Generation
          ArrayList sectionsArray = WebPageForms.getSectionsOfSingleForm ( formid, request ) ;
          for (int si = 0 ; si <sectionsArray.size() ; si++ ) {
              Object[] sectionObj = (Object[])sectionsArray.get(si);
      %>            

              <div style="border: 2px solid black; padding: 10px; margin-bottom: 10px" section="section[<%=sectionObj[0]%>]">
                  <h3><%=sectionObj[1]%></h3>
                  <hr>

                  <%
                       int sectionid = 0;
                       try {
                          sectionid = Integer.parseInt(sectionObj[0] .toString() );
                       } catch (Exception ex) {
                           Errors.setErrors("JSP forms_edit 4 " + ex.toString());
                       }

                       ArrayList sectionsFieldsArray = null;
                       if (sectionid > 0) {
                            sectionsFieldsArray = WebPageForms.getSectionFieldsArray( sectionid , formid , request);
                            if (sectionsFieldsArray != null) {
                                for (int flid = 0 ; flid < sectionsFieldsArray.size() ; flid++) {
                                    Object[] objField =  (Object []) sectionsFieldsArray.get(flid);
                                    String fidident = objField[0].toString() ;
                                    String fieldValue =  WebPageFiles.getFieldValue (fidident, dataHash ) ;                  
                                    %>
                                        <table style="width: 100%">
                                            <tr>
                                                <td style="width: 150px"><%=objField[1]%></td>
                                                <td><%=fieldValue%></td>
                                            </tr>
                                        </table>
                                    <%  
                               }
                           }
                     }
                  %>
              </div>

      <%

         }

      %>            

      </div>



      <%
          }
 }  
%>






<%

}
} catch (Exception ex) {
    Errors.setErrors("JSP file_view " + ex.toString());
}

   // out.println(request.getContextPath());
    // out.println(request.getRequestURI());
    // out.println(request.getRequestURL());

%>


