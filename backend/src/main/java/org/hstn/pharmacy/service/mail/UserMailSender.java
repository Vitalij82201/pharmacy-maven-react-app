package org.hstn.pharmacy.service.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.entity.entityUser.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMailSender {

    private final MailCreateUtil mailCreateUtil;
    private final JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendEmail(User user, String confirmationCode) {
        String html = mailCreateUtil.createConfirmationMail(
                user, // Передайте об'єкт user
                confirmationCode
        );

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject("Registration Confirmation");
            helper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException("Error while sending email", e);
        }
    }
}
