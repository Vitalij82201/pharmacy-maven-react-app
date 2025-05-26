package org.hstn.pharmacy.service.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@SpringBootTest
public class UserMailSenderTest {

    @Autowired
    private JavaMailSender mailSender;

    @Test
    public void testSendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("suharvitalij02@gmail.com");
        message.setSubject("Тест надсилання");
        message.setText("Це просто перевірка");
        mailSender.send(message);
    }
}









