package initialize;

import assets.Constants;
import assets.Encription;

import errors.Errors;
import java.sql.DriverManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;

public class Application implements ServletContextListener {
      
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        try  {
            ServletContext context = sce.getServletContext();
            String fullPath = context.getRealPath(Constants.config_file_path);
              
            File configFile = new File(fullPath);
            if(configFile.exists()) {
                   
                FileInputStream fstream = new FileInputStream(fullPath);
                InputStreamReader is = new InputStreamReader(fstream);
                BufferedReader br = new BufferedReader(is);
                  
                String strLine ;
                String val ;
                  
                  
                while ((strLine = br.readLine()) != null)   {
                        if (strLine.startsWith("host")) {
                            val = strLine.split("=")[1];
                            Constants.db_hostname = val.trim();
                        }
                          
                        if (strLine.startsWith("port")) {
                            val = strLine.split("=")[1];
                            Constants.db_port = val.trim();
                        }
                          
                        if (strLine.startsWith("username")) {
                            val = strLine.split("=")[1];
                            Constants.db_username = val.trim();
                        }
                        
                        if (strLine.startsWith("database")) {
                            val = strLine.split("=")[1];
                            Constants.db_database = val.trim();
                            System.out.println(Constants.db_database);
                                   
                        }
                        
                        if (strLine.startsWith("password")) {
                                String[] vals = strLine.split("=");
                                if (vals.length > 1) {
                                  Constants.db_password = vals[1].trim();
                                } else {
                                  Constants.db_password = "";
                                }
                        }
                          
                }
                br.close();
                is.close();
                fstream.close();
            } 
            
        } catch (Exception ex) {
            Errors.setErrors("Application / contextInitialized " + ex.toString());
        }
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Constants.dbConnection= DriverManager.getConnection("jdbc:mysql://" +Constants.db_hostname  + ":" 
                      + Constants.db_port, Constants.db_username, Constants.db_password ); 
        } catch (Exception ex) {
            Constants.dbConnection = null;
            Errors.setErrors("Application / contextInitialized 1 " + ex.toString());
        }
           

           
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
         
    }
    
    public static void reconnectDatabase () {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Constants.dbConnection= DriverManager.getConnection("jdbc:mysql://" +Constants.db_hostname  + ":" 
                      + Constants.db_port, Constants.db_username, Constants.db_password ); 
            
        } catch (Exception ex) {
            Constants.dbConnection = null;
            Errors.setErrors("Application / reconnectDatabase " + ex.toString());
        }
    }
    
    public static boolean writeConfigFile (HttpServletRequest request){
    
        boolean result = false;
        try {
           FileWriter fileWriter = new FileWriter(Constants.config_file_path);
           PrintWriter printWriter = new PrintWriter(fileWriter);
       
           Constants.db_hostname = request.getParameter("dbhost");
           Constants.db_port     = request.getParameter("dbport");
           Constants.db_username = request.getParameter("dbuser");
           Constants.db_password = request.getParameter("dbpass");
           Constants.db_database = request.getParameter("dbname");
           reconnectDatabase ();
           if (Constants.dbConnection.isValid(3)) {
                String config_text  = "host=" +Constants.db_hostname+ "\n" +
                         "port=" + Constants.db_port + "\n" +
                         "username="+ Constants.db_username+"\n" +
                         "password="+ Constants.db_password +"\n" +
                         "database="+ Constants.db_database +"\n" +
                         "login_length=3";

                printWriter.print(config_text);
                printWriter.close();
                result = true;
           }
        }catch (Exception ex) {
           Errors.setErrors("-->>>>>   Application / writeConfigFile " + ex.toString());
        }
        return result;
    
    }
    
    public static boolean setDatabaseValues (HttpServletRequest request){
        boolean result =false;
        String query = "DELETE FROM " +Constants.db_database + ".`conf` WHERE TYPE = 'SERVERKEY'";
        
        try {
            Statement st = Constants.dbConnection.createStatement();
            st.execute(query);
            
            query = "INSERT INTO  `"+Constants.db_database+ "`.`conf` (TYPE , VALUE ) "
                    + "VALUES ('SERVERKEY', ?)";
            
            
            String serverkey = request.getParameter("skey");
            PreparedStatement stmt  = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, serverkey);
            stmt.execute();
            stmt.close();
               
            query = "INSERT INTO  `" +Constants.db_database + "`.`users` (UN , EM, PS, CM ) VALUES (?, ?, ?, ? )";
            
            String username = request.getParameter("username");
            String password = Encription.getMD5(request.getParameter("pass"));
            String company_name = request.getParameter("company");
            stmt  = Constants.dbConnection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, company_name);
            stmt.execute();
            stmt.close();
            
        
        } catch (Exception ex){
            Errors.setErrors("Application / setDatabaseValues " + ex.toString());
        }
                  
        
        
        
        return result;
    }
    
    
    public static boolean isUserExists() {
        boolean result  = false; 

        String  query = "SELECT * FROM `" + Constants.db_database+"`.`users` LIMIT 1 ";
        try {
            Statement st = Constants.dbConnection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) { 
                result = true;
            }
            st.close();
            rs.close();
        } catch (Exception ex){
            Errors.setErrors("Application / isUserExists " + ex.toString());
        }
        
        return result;    
    }
    
    
}
