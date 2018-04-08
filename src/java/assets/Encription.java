package assets;

import errors.Errors;
import java.nio.ByteBuffer;

import java.security.MessageDigest;

import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

 

public class Encription {
    
    
    
    public static String getMD5  (String texttomd5) {
        String result  = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(texttomd5.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            result =  sb.toString();
        } catch (Exception ex) {
            Errors.setErrors ("Encription / getMD5 " + ex.toString());
        }
        return result;
    }
    
    public String encrypt(String word, String password, String md5factoryKey) throws Exception {
            byte[] ivBytes;
            
            SecureRandom random = new SecureRandom();
            byte bytes[] = new byte[20];
            random.nextBytes(bytes);
            byte[] saltBytes = bytes;
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance(md5factoryKey);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),saltBytes,65556,256);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            AlgorithmParameters params = cipher.getParameters();
            ivBytes =   params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedTextBytes =                          cipher.doFinal(word.getBytes("UTF-8"));
            
            byte[] buffer = new byte[saltBytes.length + ivBytes.length + encryptedTextBytes.length];
            System.arraycopy(saltBytes, 0, buffer, 0, saltBytes.length);
            System.arraycopy(ivBytes, 0, buffer, saltBytes.length, ivBytes.length);
            System.arraycopy(encryptedTextBytes, 0, buffer, saltBytes.length + ivBytes.length, encryptedTextBytes.length);
            
            String result = new Base64().encodeToString(buffer);
            
            return result;
    }
    
     public String decrypt(String encryptedText, String password, String md5factoryKey) throws Exception {
           
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            
            ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(encryptedText));
            byte[] saltBytes = new byte[20];
            buffer.get(saltBytes, 0, saltBytes.length);
            byte[] ivBytes1 = new byte[cipher.getBlockSize()];
            buffer.get(ivBytes1, 0, ivBytes1.length);
            byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];

            buffer.get(encryptedTextBytes);
            
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance(md5factoryKey);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65556, 256);
            SecretKey secretKey = factory.generateSecret(spec);
            SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));
            byte[] decryptedTextBytes = null;
            try {
              decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
            } catch (Exception e) {
                e.printStackTrace();
            } 

            return new String(decryptedTextBytes);
    }
    
}
