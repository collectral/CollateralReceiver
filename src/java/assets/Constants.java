package assets;

import java.sql.Connection;

public class Constants {
    
    public static final boolean isDebbaging = true; // should be errors seen and reported
    public static final String  gitst_cookie = "gitstkey"; // key is stored in user ckookie to allow loging in 
    public static       int     login_length =  0;
    
    public static final String  collectralurl = "http://collectral.com/servers";
    public static final String  page_attribute  = "pagetype";
    public static final String  user_attribute  = "userkey";
    
    public static final int page_home     = 0;
    public static final int page_file     = 1;
    public static final int page_form     = 2;
    public static final int page_devices  = 3;
    public static final int page_settings = 4;
    
    /// ################ DATABASE DEFAULT CONFIGURATION #############
    public static String db_database = ""; //  default configuration database name 
    public static String db_username = "";            //  default configuration database name 
    public static String db_password = "";                //  default configuration database name 
    public static String db_hostname = "";       //  default configuration database name 
    public static String db_port     = "";            //  default configuration database name 
    
    public static Connection dbConnection = null;    
    public static int db_timeout = 10;   
    //// ############### 
    public static final String config_file_path = "/WEB-INF/config/config"; 
    
    //// ####### Configuration details 
    public static String conf_COMPANY   = "Collectral";
    public static String conf_SERVERKEY = "";
    
    
    
}
