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
} catch (Exception ex) {
    Errors.setErrors("JSP file_view " + ex.toString());
}



if (fileid > 0 ) {
   Object [] obj =  WebPageFiles.getFileJson(fileid);
   
   
   
   HashMap dataHash = (HashMap)obj[3];
   out.println( dataHash.toString());
   
   int formid = 0;
   
    try {
        formid = Integer.parseInt(obj[2].toString()); 
    } catch (Exception ex) {
        Errors.setErrors("JSP file_view " + ex.toString());
    }
  
  if (formid > 0) {
    
    String contectxt_url_home_user_add_user = request.getContextPath();
    Object[] id_key_inc = (Object[])request.getAttribute(Constants.user_attribute);
    
    ArrayList groupListOfForms = WebPageForms.getAllGroups(request);
    String[] formName = WebPageForms. getSingleFormValues (formid,  request );
       

%>

<div style="padding: 20px">
 
    <form id="mainform" method="POST" action="<%=contectxt_url_home_user_add_user%>/Forms" > 
        <div id="form_content">
                <input name="form_name" type="text" placeholder="Add Form Name" style="width: 73%" value="<%=formName%>" required >
                <select name="assignedgroups" style="font-size: 14px; padding-top: 9px ;padding-bottom: 9px">
                        <option  value="0">Select Group</option>
                        <%
                            int gid = -1;
                            for (int fie= 0 ; fie< groupListOfForms.size() ; fie++) {
                                   Object[] objGrp = (Object[])groupListOfForms.get(fie);
                                   
                                   try {
                                     gid = Integer.parseInt(objGrp[0].toString());
                                   } catch (Exception ex) {
                                     Errors.setErrors("JSP forms_edit 3 / gid " + ex.toString());
                                   }
                                       %>
                                            <option value="<%=objGrp[0]%>"><%=objGrp[1]%></option>
                                        <%          
                                
                            }
                        %>
                         
                </select>
            <a id="section_delete" class="gitst-button" style="padding: 5px;" onclick="setNewSection(this)">ADD SECTION</a>
           
            <hr style="visibility: hidden">
<%
    
    ///// Section Generation
    
    ArrayList sectionsArray = WebPageForms.getSectionsOfSingleForm ( formid, request ) ;
    
    for (int si = 0 ; si <sectionsArray.size() ; si++ ) {
        Object[] sectionObj = (Object[])sectionsArray.get(si);
        

%>            
            
        <div style="border: 2px solid black; padding: 10px; margin-bottom: 10px" section="section[<%=sectionObj[0]%>]">
            <input type="text"   name="sectionname[<%=sectionObj[0]%>]"  value="<%=sectionObj[1]%>"  style="width: 515px" />
            <input type="hidden" name="sectionident"  value="<%=sectionObj[0]%>"/>
            <a id="section_delete" param="<%=sectionObj[0]%>"  class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="setNewField(this)">ADD FIELD</a>
            <a id="section_delete" param="<%=sectionObj[0]%>"  class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="deleteSection(this)">DELETE</a>
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
                    sectionsFieldsArray = WebPageForms.getSectionFieldsArray( sectionid , formid , request) ;
                 }
                 

                 if (sectionsFieldsArray != null) {
                     for (int flid = 0 ; flid < sectionsFieldsArray.size() ; flid++) {
                         
                         Object[] objField =  (Object []) sectionsFieldsArray.get(flid);
                         String fidident = sectionid + "_"  + objField[0] ;
                         
                     
            %>
                        <div>
                            <input type="hidden" name="fieldident"  value="<%=fidident%>" />
                            <input type="text" name="fieldName[<%=fidident%>]"    value="<%=objField[1]%>"  style="width: 150px" />
                                   <input type="text" name="fieldDefault[<%=fidident%>]" value="<%=objField[2]%>"   style="width: 148px" />
                                   
                                   <%
                                       String selectedText = "";
                                       String selectedNumeric = "";
                                       String selectedDate = "";
                                       String selectedAddress = "";
                                       String selectedCamera = "";
                                       
                                       if (objField[3].toString().equals("1")) {
                                            selectedText = "selected";
                                       }
                                       
                                       if (objField[3].toString().equals("2")) {
                                            selectedNumeric = "selected";
                                       }
                                       
                                       if (objField[3].toString().equals("3")) {
                                            selectedDate = "selected";
                                       }
                                      
                                       if (objField[3].toString().equals("4")) {
                                            selectedAddress = "selected";
                                       }
                                       
                                       if (objField[3].toString().equals("5")) {
                                            selectedCamera = "selected";
                                       }
                                   %>
                                   <select name="field_type[<%=fidident%>]" style="font-size: 14px; padding-top: 9px ;padding-bottom: 9px">\
                                       <option value="0">Select Type</option>
                                       <option value="1" <%=selectedText%>>Text</option>
                                       <option value="2" <%=selectedNumeric%>>Numeric</option>
                                       <option value="3" <%=selectedDate%>>Date</option>
                                       <option value="4" <%=selectedAddress%>>Address</option>
                                       <option value="5" <%=selectedCamera%>>Camera</option>
                                   </select>
                                   <%
                                      if (objField[4].toString().equals("1")) {
                                   %>      
                                      <input type="checkbox" name="mandatory[<%=fidident%>]" checked> Mandatory 
                                   <%
                                      }  else {
                                   %>
                                    <input type="checkbox" name="mandatory[<%=fidident%>]"> Mandatory 
                                   <%
                                      }
                                   %>    
                                  
                                   
                                   <%
                                      if (objField[5].toString().equals("1")) {
                                   %>      
                                      <input type="checkbox" name="visible[<%=fidident%>]" checked>   Visible   
                                   <%
                                      }  else {
                                   %>
                                    <input type="checkbox" name="visible[<%=fidident%>]">   Visible 
                                   <%
                                      }
                                   %>    
                                  
                                   
                                   <a param="<%=fidident%>" id="section_delete" class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="deleteField(this)">DELETE</a>
                                   <hr>
                        </div>
            
            <%  
                    }
                }
            %>
                
        </div>
            
<%

   }

%>            
  
        </div>
    <input type="hidden" name="posting" value="edit_form">  
    <input type="hidden" name="formid"  value="<%=formid%>"> 
    <input type="submit" value="SAVE"  class="gitst-button">    
</form>


<div style="visibility:  hidden; height: 0px">
    
    <div id="new_field" >
            <div>
                <input type="hidden" name="fieldident"  value="{sid}_{fid}" />
                <input type="text" name="fieldName[{sid}_{fid}]"    placeholder="Add Field Name "  style="width: 150px" />
                <input type="text" name="fieldDefault[{sid}_{fid}]" placeholder="Default Value"  style="width: 148px" />
                <select name="field_type[{sid}_{fid}]" style="font-size: 14px; padding-top: 9px ;padding-bottom: 9px">\
                    <option value="0">Select Type</option>
                    <option value="1">Text</option>
                    <option value="2">Numeric</option>
                    <option value="3">Date</option>
                    <option value="4">Address</option>
                    <option value="5">Camera</option>
                </select>
                    
                <input type="checkbox" name="mandatory[{sid}_{fid}]"> Mandatory 
                <input type="checkbox" name="visible[{sid}_{fid}]">   Visible  
                <a param="{sid}_{fid}" id="section_delete" class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="deleteField(this)">DELETE</a>
                <hr>
            </div>
            
    </div> 
    
    
    <div id="new_section">
         
        <div style="border: 2px solid black; padding: 10px; margin-bottom: 10px" section="section[{sid}]">
            
            <input type="text"   name="sectionname[{sid}]" placeholder="Add Section Name"  style="width: 515px" />
            <input type="hidden" name="sectionident"  value="{sid}"/>
            <a id="section_delete" param="{sid}"  class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="setNewField(this)">ADD FIELD</a>
            <a id="section_delete" param="{sid}"  class="gitst-button"  style="padding-top: 5px;padding-bottom: 5px;" onclick="deleteSection(this)">DELETE</a>
            <hr>
             
        </div>
    </div> 
</div>  
    
</div>


<div style=" visibility:  hidden; height: 0px">
    
    <script>
        
        function setNewSection(object) {
            var sectionid = $.now();
            var html = $("#new_section").html();
            while (html.indexOf("{sid}") !== -1) {
                 html = html.replace("{sid}", sectionid); 
            }
            $('#form_content').append(html);
            return false;
        }
         
         
        
        function setNewField(object_field) {
            
           var fieldid = $.now();
           var sectionid = object_field.getAttribute("param");
           var html = $("#new_field").html();
           while (html.indexOf("{sid}") !== -1) {
                html = html.replace("{sid}", sectionid); 
           }
           
           while (html.indexOf("{fid}") !== -1) {
                html = html.replace("{fid}", fieldid); 
           }
           
           $("div[section=\"section[" +  sectionid + "]\"]").append(html);
           
           return false;
        }
        
        function deleteSection(object_field) {
           
           var sectionid = object_field.getAttribute("param");
           $( "div[section=\"section[" +  sectionid + "]\"]" ).remove();
           return false;
        }
        
        function deleteField(object_field) {
           object_field.parentNode.parentNode.removeChild(object_field.parentNode);
           return false;
        }
        
    </script>
</div>

<%
   
 }  
%>






<%

}

   // out.println(request.getContextPath());
    // out.println(request.getRequestURI());
    // out.println(request.getRequestURL());

%>


