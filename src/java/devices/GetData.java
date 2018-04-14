package devices;

import errors.Errors;
import javax.servlet.http.HttpServletRequest;

public class GetData {
    
    public static String getResponce (HttpServletRequest request) {
        String result  = "0";
    
        String device_key = request.getParameter(ClassConstants.gitst_device_keys);
        String [] keys_vals = device_key.split("_");
        String outpute = "0";
            
        if (keys_vals.length > 1) {
            try {
              int deviceid = Integer.parseInt(keys_vals[0]);
            } catch (Exception ex) {
               Errors.setErrors("ServletTemplates / processRequest " + ex.toString());
            }
        }
        
        return result;
    }
    
    
}
