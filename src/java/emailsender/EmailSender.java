package emailsender;

import errors.Errors;
import java.util.Properties;

import javax.mail.Message;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    
   public static void sendRegistrationEmail(String recipient, String subject, String textBody   )   {
       
      final String username="";
      final String password="";
      final String fromemail="info@collectral.com";    
      
      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

         try {
            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                   @Override
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username, password);
                   }
            });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromemail));
            
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(fromemail));
            
            message.setSubject(subject);
            message.setText(textBody);

            Transport.send(message);

        } catch (Exception ex) {
            Errors.setErrors("EmailSender /  sendRegistrationEmail " + ex.toString());
        }
   }
    
}
