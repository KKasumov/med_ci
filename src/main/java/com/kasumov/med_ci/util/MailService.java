package com.kasumov.med_ci.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void send(String mailTo, String subject, String message) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom(username);
        smm.setTo(mailTo);
        smm.setSubject(subject);
        smm.setText(message);
        mailSender.send(smm);
    }
}
