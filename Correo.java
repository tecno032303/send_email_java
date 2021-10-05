import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Correo {
    
    private final String email = "email@gmail.com";
    private final String emailPassword = "password";
    private final String host = "smtp.gmail.com";
    private final String puerto_TLS_STARTTLS = "587";
    private final String puerto_SSL = "465";

    public void sendEmail(String asunto, String mensaje, String destino) throws MessagingException,
            UnsupportedEncodingException {
        Properties mailPropiedad = new Properties();
        
        mailPropiedad.put("mail.smtp.from",email );
        mailPropiedad.put("mail.smtp.host",host );
        mailPropiedad.put("mail.smtp.port", puerto_TLS_STARTTLS);
        mailPropiedad.put("mail.smtp.auth", true);
        mailPropiedad.put("mail.smtp.socketFactory.port", puerto_SSL);
        mailPropiedad.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailPropiedad.put("mail.smtp.socketFactory.fallback", "false");
        mailPropiedad.put("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getDefaultInstance(mailPropiedad, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, emailPassword);
            }

        });

        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(email));
        String[] emails = {destino};
        InternetAddress dests[] = new InternetAddress[emails.length];
        for (int i = 0; i < emails.length; i++) {
            dests[i] = new InternetAddress(emails[i].trim().toLowerCase());
        }
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject(asunto, "UTF-8");
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(mensaje, "text/html;charset=utf-8");
        mp.addBodyPart(mbp);
        message.setContent(mp);
        message.setSentDate(new java.util.Date());

        Transport.send(message);
    }

}
