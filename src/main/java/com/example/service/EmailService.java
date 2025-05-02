package com.example.service;

import com.example.domain.enums.EmailEnum;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    @Autowired
    private JavaMailSender mailSender;
    public void multiSend(String subject, String text) {
        for (EmailEnum email : EmailEnum.values()) {
            send(subject, text, email.getAddress());
        }
    }
    public void send(String subject, String text, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("youreamil@naver.com");  // 발신자 이메일
            helper.setTo(email);  // 수신자 이메일
            helper.setSubject(subject);
            helper.setText(text);

            mailSender.send(message);
            logger.info("메일이 발송되었습니다.");
        } catch (MessagingException e) {
            logger.error(String.format("이메일(%s) 발송 실패: %s", email, e.getMessage()));
        }
    }
}

