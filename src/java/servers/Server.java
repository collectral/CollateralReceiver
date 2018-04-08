package servers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Server extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
           String respond = "0";
           String key =  request.getParameter("SERVERKEY");
           if (key != null) {
               String [] ackey = key.split("_");
               int adminid = Integer.parseInt(ackey[0]);
               String connectionip = request.getLocalAddr();
               if (ServerAccess.isAccessAllowed(adminid, key, connectionip)) {
                   respond = ServerAccess.getAllData(adminid);
               }
           } else {
               String login = request.getParameter("SERVERLOGIN");
               if (login != null) {
                    String password = request.getParameter("SERVERPASSWORD");
                    respond = ServerAccess.getAdminServerKey(login, password);
               }
           }
           out.print(respond);
        }
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
         
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
