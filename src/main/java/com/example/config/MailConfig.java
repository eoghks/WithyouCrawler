package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${mail.userName}")
    private String userName;

    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(465);  // 포트 465 또는 587
        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "false");

        // SSL 인증서 검증을 우회하려면 아래 설정을 추가
        props.put("mail.smtp.ssl.trust", "*");  // 모든 SSL 인증서를 신뢰
        props.put("mail.smtp.connectiontimeout", "10000");  // 연결 타임아웃
        props.put("mail.smtp.timeout", "10000");  // 메일 발송 타임아웃

        return mailSender;
    }
}
