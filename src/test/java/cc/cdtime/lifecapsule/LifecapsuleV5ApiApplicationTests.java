package cc.cdtime.lifecapsule;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@SpringBootTest
class LifecapsuleV5ApiApplicationTests {

    @Test
    void contextLoads() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("cdtime117@gmail.com", "qvbh akoe fapx lbnn");
            }
        });

        String from = "flow_ocean@yahoo.com";
        String to = "flow_ocean@yahoo.com";
        String subject = "测试邮件";
        String htmlBody = "<div><h1>这是一封邮件</h1><div>吃饭了吗？</div></div>";
        String textBody = "This email was sent with Amazon SES using the AWS SDK for Java.";

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cdtime117@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("7020659@qq.com"));
            message.setSubject("Test for lifecapsule email notification");
            message.setText(htmlBody);

            Transport.send(message);
            System.out.println("Email send successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
