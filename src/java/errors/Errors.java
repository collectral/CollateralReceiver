package errors;

import assets.Constants;

public class Errors {
     
    public static void setErrors (String errorText) {
        if (Constants.isDebbaging) {
            System.out.println(errorText);
        }
    }
}
