package assets;

import errors.Errors;
import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class  HTTPSPost {
        
        
        private final static String USER_AGENT = "Mozilla/5.0";
    
	public static String sendPost(String url, String  serverkey,  String severurl) {
                String result = "";
                
                System.out.println("url : " + url);
                
                try {
                    url = url.trim();
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("SKEY", serverkey);
                    parameters.put("DURL", severurl);
                    result = makePostRequest(url, parameters);
                    System.out.println("result " +  result);
                } catch (Exception ex) {
                    Errors.setErrors("" + ex.toString());
                }
                return result ;
	}
    
    static {
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null; // Not relevant.
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Do nothing. Just allow them all.
                    }
                }
        };

        HostnameVerifier trustAllHostnames = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true; // Just allow them all.
            }
        };

        try {
            System.setProperty("jsse.enableSNIExtension", "false");
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCertificates, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(trustAllHostnames);
        } catch (GeneralSecurityException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

   
    public static String makePostRequest(String url, Map<String, String> parameters) {
       
        String result = "";
        try {
            ensureAllParametersArePresent(parameters);
            
            HttpsURLConnection con = getUrlConnection(url);
            String urlParameters = processRequestParameters(parameters);
            
            sendPostParameters(con, urlParameters);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
               result += inputLine;
            }
            
            in.close();
              
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return result;
    }
    
    private static void sendPostParameters(URLConnection con, String urlParameters) throws IOException {
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();
    }
       
    private static HttpsURLConnection getUrlConnection(String url) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        return con;
    }

    private static void ensureAllParametersArePresent(Map<String, String> parameters) {
        if (parameters.get("send") == null) {
            parameters.put("send", "Envoyer votre message");
        }
        if (parameters.get("phone") == null) {
            parameters.put("phone", "");
        }
    }

     
    private static String processRequestParameters(Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder();
        for (String parameterName : parameters.keySet()) {
            sb.append(parameterName).append('=').append(urlEncode(parameters.get(parameterName))).append('&');
        }
        return sb.substring(0, sb.length() - 1);
    }

    
    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // This is impossible, UTF-8 is always supported according to the java standard
            throw new RuntimeException(e);
        }
    }

}
