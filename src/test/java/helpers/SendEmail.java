package helpers;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Properties;

import static helpers.Utils.readFile;

public class SendEmail {
    private static SendEmail tlsSender = new SendEmail("rozetkatests@gmail.com", "QW_01298");
    private String username;
    private String password;
    private Properties props;

    private SendEmail(String username, String password) {
        this.username = username;
        this.password = password;

        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }

    public static void sendEmails(String fileName) {
        LinkedList<String> emailList = readFile("Emails.txt");
        for (String email : emailList) {
            tlsSender.send("This is Rozetka test", "This is rozetka test!", "rozetkatests@gmail.com", email, fileName);
        }
    }

    private void send(String subject, String text, String fromEmail, String toEmail, String fileName) {
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent("Test", "text/plain; charset=" + "UTF-8" + "");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(fileName);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            try {
                attachmentBodyPart.setFileName(MimeUtility.encodeText(source.getName()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
