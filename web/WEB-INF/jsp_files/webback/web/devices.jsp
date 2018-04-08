<%@page import="web.WebPageDevices"%>
<%@page import="java.util.ArrayList"%>
<jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/post_handler.jsp"></jsp:include>

<%   
    ArrayList groupList  = WebPageDevices.getAllGroup(request);
    Object[] groupData = null;
    String contectxt_url_home_user = request.getContextPath();  
    
    
%>


<div class="gitst-layout-wrapper">
                <div class="gitst-content-layout">
                    <div class="gitst-content-layout-row">
                        <div class="gitst-layout-cell gitst-sidebar1">
                            <div class="gitst-vmenublock clearfix">
                                 
                                    <div class="gitst-vmenublockcontent">
                                        <ul class="gitst-vmenu">
                                                <li><a href="<%=contectxt_url_home_user%>/Devices/AddDevice">Add Device</a></li>
                                                <li><a href="<%=contectxt_url_home_user%>/Devices/AddDeviceGroup" >Add Device Group</a></li>
                                        </ul>        
                                    </div>
                                
                                    <hr>
                                    <div class="gitst-vmenublockheader">
                                        <h3 class="t">Groups List</h3>
                                    </div>
                                    <div class="gitst-vmenublockcontent">
                                        <ul class="gitst-vmenu">
                                            <% for (int i  =0 ; i <groupList.size() ; i++ ) { 
                                                groupData =(Object[]) groupList.get(i);
                                                
                                                String groupStr = "Group=" + groupData[0];
                                                 
                                                if (request.getRequestURL().toString().endsWith(groupStr)) {
                                                   %> 
                                                       <li><a href="<%=contectxt_url_home_user%>/Devices/<%=groupStr%>" class="active"><%=groupData[1]%></a></li>
                                                   <% 
                                                } else {
                                                   %> 
                                                       <li><a href="<%=contectxt_url_home_user%>/Devices/<%=groupStr%>"><%=groupData[1]%></a></li>
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
                                    
                                      if (request.getRequestURL().toString().endsWith("AddDeviceGroup")) {
                                          %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/group_add.jsp"></jsp:include><%
                                      } else  if ( request.getRequestURL().toString().contains("AddDevice")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/device_add.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("EditDeviceGroup=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/group_edit.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("DeleteDeviceGroup=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/group_delete.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("Group=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/device_group_list.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("EditDevice=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/device_edit.jsp"></jsp:include><%
                                      } else if (request.getRequestURL().toString().contains("DeleteDevice=")) {
                                           %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/device_delete.jsp"></jsp:include><%
                                      } else {
                                          %><jsp:include page="/WEB-INF/jsp_files/webback/web/DeviceArticle/devices_list.jsp"></jsp:include><%
                                      }

                                %>     
                                 
                            </article>
                        </div>
                    </div>
                </div>
            </div>