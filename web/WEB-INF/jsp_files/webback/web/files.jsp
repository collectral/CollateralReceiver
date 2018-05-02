<%@page import="web.WebPageFiles"%>

<%@page import="java.util.ArrayList"%>


<%
    
    String contectxt_url_home_user = request.getContextPath();
    // ArrayList listOfTemplates  = 
    // out.println(request.getContextPath());
    // out.println(request.getRequestURI());
    // out.println(request.getRequestURL());
    

    ArrayList formList  = WebPageFiles.getFormList();


%>

<div class="gitst-layout-wrapper">
                <div class="gitst-content-layout">
                    <div class="gitst-content-layout-row">
                        <div class="gitst-layout-cell gitst-sidebar1">
                            <div class="gitst-vmenublock clearfix">
                                
                                <%
                                    if (request.getRequestURL().toString().contains("Form=NT")) {
                                %>
                                        <div class="gitst-vmenublockcontent">
                                            <ul class="gitst-vmenu">
                                                    <li><a href="<%=contectxt_url_home_user%>/Files/AddFile" class="active" >Add File</a></li>
                                            </ul>        
                                        </div>
                                        <hr>
                                <%
                                    }
                                %>
                                    <div class="gitst-vmenublockheader">
                                        <h3 class="t">Form List</h3>
                                    </div>
                                    <div class="gitst-vmenublockcontent">
                                        <ul class="gitst-vmenu">
                                             
                                            <%  
                                                for (int frmi =0; frmi<formList .size() ; frmi++) {
                                                    Object[] objForms = (Object[])formList.get(frmi);
                                            %>
                                             <li><a href="<%=contectxt_url_home_user%>/Files/Form=<%=objForms[0]%>" class="active" ><%=objForms[1]%></a></li>
                                            <%  
                                                }
                                            %>
                                        </ul>        
                                    </div>
                            </div>
                        </div>
                        <div class="gitst-layout-cell gitst-content">
                             <article class="gitst-post gitst-article" id="users_working_dir">
                                
                                <%
                                       if ( request.getRequestURL().toString().contains("Images"))  {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/file_image_viewer.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().endsWith("AddFile"))  {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/files_add_new.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("Form=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/files_listPerForm.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().contains("Edit=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/files_edit.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().contains("View=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/file_view.jsp"></jsp:include><%
                                      } else {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FilesArticle/files_list.jsp"></jsp:include><%
                                      }
                                %>     
                                 
                            </article>
                        </div>
                    </div>
                </div>
            </div>