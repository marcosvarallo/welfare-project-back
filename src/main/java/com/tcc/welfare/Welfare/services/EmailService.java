package com.tcc.welfare.Welfare.services;

import com.tcc.welfare.Welfare.repository.EmailSender;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    @Async
    public void send(String to, String email){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Welfare - Confirmação de Email");
            helper.setFrom(from);
            mailSender.send(mimeMessage);
        } catch(MessagingException e) {
            LOGGER.error("Fail to send email!", e);
        throw new IllegalStateException("Failed to send email!");
        }
    }

    @Override
    @Async
    public void sendPassword(String to, String email){
        try{
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Welfare - Recuperação de Senha");
            helper.setFrom(from);
            mailSender.send(mimeMessage);
        } catch(MessagingException e) {
            LOGGER.error("Fail to send email!", e);
            throw new IllegalStateException("Failed to send email!");
        }
    }

}
