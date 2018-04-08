<%@page import="web.WebPageForms"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/post_handler.jsp"></jsp:include>

<%
    String contectxt_url_home_user = request.getContextPath();
    ArrayList formsGroupData =  WebPageForms.getAllGroups(request);
    Object[] formGroupData = null;
%>

<div class="gitst-layout-wrapper">
                <div class="gitst-content-layout">
                    <div class="gitst-content-layout-row">
                        <div class="gitst-layout-cell gitst-sidebar1">
                            <div class="gitst-vmenublock clearfix">
                                 
                                    <div class="gitst-vmenublockcontent">
                                        <ul class="gitst-vmenu">
                                            <li><a href="<%=contectxt_url_home_user%>/Forms/AddForm" class="active" >Add Form</a></li>
                                        </ul>        
                                    </div>
                                
                                    <hr>
                                    <div class="gitst-vmenublockheader">
                                        <h3 class="t">Groups</h3>
                                    </div>
                                    <div class="gitst-vmenublockcontent">
                                        <ul class="gitst-vmenu">
                                                
                                            <% 
                                                for (int i  =0 ; i <formsGroupData.size() ; i++ ) { 
                                                      formGroupData = (Object[])formsGroupData.get(i);
                                                      String groupStr = "Group=" + formGroupData[0];
                                                      if (request.getRequestURL().toString().endsWith(groupStr)) {
                                                         %> 
                                                             <li><a href="<%=contectxt_url_home_user%>/Forms/<%=groupStr%>" class="active"><%=formGroupData[1]%></a></li>
                                                         <% 
                                                      } else {
                                                         %> 
                                                              <li><a href="<%=contectxt_url_home_user%>/Forms/<%=groupStr%>"><%=formGroupData[1]%></a></li>
                                                         <% 
                                                      }
                                                }
                                            %>
                                            
                                        </ul>        
                                    </div>
                            </div>
                        </div>
                        <div class="gitst-layout-cell gitst-content">
                             <article class="gitst-post gitst-article" id="users_working_dir">
                                <%
                                      if ( request.getRequestURL().toString().endsWith("AddForm")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/forms_add_new.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().contains("Edit=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/forms_edit.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().contains("Disable=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/forms_disable.jsp"></jsp:include><%
                                      } else if ( request.getRequestURL().toString().contains("Group=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/forms_list_in_group.jsp"></jsp:include><%
                                      } else {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/FormsArticle/forms_list.jsp"></jsp:include><%
                                      }
                                %>     
                                 
                            </article>
                        </div>
                    </div>
                </div>
            </div>