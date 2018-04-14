package devices;

public class ClassConstants {
    
    public static final String gitst_device_keys  = "dkey"; // Action which should be performed it comes with key
    public static final String gitst_device_forms = "formslist";    
    public static final String json               = "json";
    public static final String formid             = "formid";
    
    public static final int action_device_register       = 1;
    public static final int action_device_get_templates  = 2;
    public static final int action_device_receive_data   = 3;
    public static final int action_device_send_data      = 4;
    
    public static final int status_device_wrong_key      = -2;
    public static final int status_device_no_info        = -1;
    public static final int status_device_not_registred  = 0;
    public static final int status_device_registred      = 1;
    public static final int status_device_disabled       = 2;
    public static final int status_device_blocked        = 3;
    
    public static final int TYPE_TEXT = 1;
    public static final int TYPE_NUMERIC = 2;
    public static final int TYPE_DATE = 3;
    public static final int TYPE_ADDRESS= 4;
    public static final int TYPE_CAMERA= 5;
    public static final int TYPE_MULTIPLESELECT = 6;
    public static final int TYPE_SINGLESELECT  = 7;
    
    public static final String posting              = "posting";
    public static final String posting_data         = "data";
    public static final String posting_get_form     = "get_form";
    public static final String posting_get_forms    = "get_forms";
    public static final String posting_get_data     = "get_data";
    public static final String posting_post_data    = "post_data";
    public static final String posting_registration = "registration";
    
    public static final String gitst_device_serial  = "skey";
    public static final String gitst_device_id      = "did";
   
    
}
