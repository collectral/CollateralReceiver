<%@page import="web.WebConstants"%>
<%@page import="java.util.ArrayList"%>
<%@page import="web.WebPageForms"%>
<%@page import="errors.Errors"%>
<%@page import="assets.Constants"%>
<%
    
    try {
    String contectxt_url_home_user_add_user = request.getContextPath();
    Object[] id_key_inc = (Object[])request.getAttribute(Constants.user_attribute);
    int userid  = 0;
    try {
        userid = Integer.parseInt(id_key_inc[0].toString().trim());
    } catch (Exception ex) {
        Errors.setErrors("JSP / forms_add_new / " + ex.toString());
    }
    ArrayList groupListOfForms = WebPageForms.getAllGroups(request);
    
    if (userid > 0) {

%>

<div style="padding: 20px">
 
    <form id="mainform" method="POST" action="<%=contectxt_url_home_user_add_user%>/Forms" > 
        <div id="form_content">
            <input name="form_name" type="text" placeholder="Add Form Name" style="width: 73%; height: 10px" required >
            
                <select name="assignedgroups" style="font-size: 14px; padding-top: 4px ;padding-bottom: 4px; height: 30px">
                        <option  value="0">Select Group</option>
                        <%
                            for (int ijg = 0 ; ijg < groupListOfForms.size() ; ijg++) {
                                   Object[] objGrp = (Object[])groupListOfForms.get(ijg);
                        %>
                                  <option value="<%=objGrp[0]%>"><%=objGrp[1]%></option>
                        <%
                            }
                        %>
                         
                </select>
            <a id="section_delete" class="gitst-button" style="padding: 1px 3px 4px 5px;height: 23px" onclick="setNewSection(this)">ADD SECTION</a>
           
            <hr style="visibility: hidden">
        
            <hr style="visibility: hidden">
        </div>
    <input type="submit" value="SAVE"  class="gitst-button" >    
    <input type="hidden" name="posting" value="new_form">    
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
                    <option value="6">Multi Select</option>
                    <option value="7">Single Select</option>
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
} catch (Exception ex) {
    out.print(ex.toString());

}
%>
