# WithyouCrawler
![image.png](image/crawler.png)
---

### 주요 기능

- 지정된 URL의 공고 목록을 일정 주기로 크롤링
- 이전에 검색된 공고 수보다 많아졌을 경우 이메일 자동 발송
- Quartz를 활용한 스케줄링

---

### 개발 기간 및 과정

---

### 개발 환경

- **IDE**: IntelliJ IDEA
- **Java**: 17
- **Spring Boot**: 3.4.5
- **Build Tool**: Gradle 8.1.3

---

### 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.4.5
- **스케줄링**: Quartz Scheduler
- **HTML 파싱**: Jsoup
- **이메일 발송**: JavaMailSender

---

### 프로젝트 구조
```
src
└── main
├── java
│ └── com.example
│     ├── config # Quartz 및 Mail 설정
│     ├── scheduler # CrawlingJob 구현체
│     └── service # EmailService
└── resources
    └── application.yml # Spring 및 기타 설정
```

---

### 주의 사항

- **운영 환경 고려 부족**: 본 프로젝트는 개발 및 테스트 환경을 기준으로 작성되었으며, 실제 운영 환경에서는 보안, 성능, 안정성 등의 요소를 고려해야 합니다.
- **이메일 설정**:  네이버의 경우 **IMAP/SMTP 설정을 사용함**으로 변경해야 합니다.
- **SSL 인증서**: **SSL 인증서 검증을 우회하는 설정이 포함**되어 있으며, 운영 환경에서 사용 시 **보안 문제**가 발생할 수 있습니다. **실제 서비스에서 사용할 때는 적절한 SSL 인증서와 검증 절차를 마련해야 합니다.**
- **네이버 앱 비밀번호**: 본 프로젝트에서 사용되는 이메일 설정은 네이버의 앱 비밀번호를 사용하며, 해당 비밀번호는 보안 상의 이유로 **직접 설정해야** 합니다.

---

### 설정 방법(MailConfig.java, EmailService.java, EmailEnum)

코드를 수정하거나 설정 파일을 변경하는 방법에 대해 설명합니다.

- MailConfig.java
    
    ```java
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(465);  // 포트 465 또는 587
        mailSender.setUsername("yourEmail@naver.com");
        mailSender.setPassword("yourPassword");  // 네이버 앱 비밀번호
    
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
    ```
    
    **본인 이메일 및 앱 비밀번호 입력**
    
    - mailSender.setUsername("yourEmail@naver.com");
    - mailSender.setPassword("yourPassword");
- EmailService.java
    
    ```java
    public void send(String subject, String text, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("yourEmail@naver.com");  // 발신자 이메일
            helper.setTo(email);  // 수신자 이메일
            helper.setSubject(subject);
            helper.setText(text);
    
            mailSender.send(message);
            logger.info("메일이 발송되었습니다.");
        } catch (MessagingException e) {
            logger.error(String.format("이메일(%s) 발송 실패: %s", email, e.getMessage()));
        }
    }
    ```
    
    MailConfig의 youEmail과 동일한 이메일 입력
    
    - helper.setFrom(”yourEmail@naver.com");
- EmailEnum
    
    ```java
    public enum EmailEnum {
        EOGHKS("zhfldk7316@naver.com");
    
        private final String address;
    
        EmailEnum(String address) {
            this.address = address;
        }
    
        public String getAddress() {
            return address;
        }
    }
    ```
    
    EOGHKS("zhfldk7316@naver.com") 대신 수신 받고 싶은 이메일 입력, **enum에 기재된 메일로 모두 전송되므로 수신 받고 싶은 이메일을 해당 형식으로 열거**
    

---

### 네이버 이메일 설정

---

### 주요 기능

- 지정된 URL의 공고 목록을 일정 주기로 크롤링
- 이전에 검색된 공고 수보다 많아졌을 경우 이메일 자동 발송
- Quartz를 활용한 스케줄링

---

### 개발 기간 및 과정
| 날짜            | 작업       | 태그                   | 내용                                                                 |
|-----------------|------------|------------------------|----------------------------------------------------------------------|
| 2025년 5월 2일  | 1차 개발   | 문서 작성, 개발       | 1. 공고 크롤링 기능 개발<br>2. 이메일 발송 기능 개발<br>3. 문서 작성 |

---

### 개발 환경

- **IDE**: IntelliJ IDEA
- **Java**: 17
- **Spring Boot**: 3.4.5
- **Build Tool**: Gradle 8.1.3

---

### 기술 스택

- **언어**: Java 17
- **프레임워크**: Spring Boot 3.4.5
- **스케줄링**: Quartz Scheduler
- **HTML 파싱**: Jsoup
- **이메일 발송**: JavaMailSender

---

### 프로젝트 구조

src  
└── main
├── java
│ └── com.example
│ ├── config # Quartz 및 Mail 설정
│ ├── scheduler # CrawlingJob 구현체
│ └── service # EmailService
└── resources
└── application.yml # Spring 및 기타 설정


---

### 주의 사항

- **운영 환경 고려 부족**: 본 프로젝트는 개발 및 테스트 환경을 기준으로 작성되었으며, 실제 운영 환경에서는 보안, 성능, 안정성 등의 요소를 고려해야 합니다.
- **이메일 설정**:  네이버의 경우 **IMAP/SMTP 설정을 사용함**으로 변경해야 합니다.
- **SSL 인증서**: **SSL 인증서 검증을 우회하는 설정이 포함**되어 있으며, 운영 환경에서 사용 시 **보안 문제**가 발생할 수 있습니다. **실제 서비스에서 사용할 때는 적절한 SSL 인증서와 검증 절차를 마련해야 합니다.**
- **네이버 앱 비밀번호**: 본 프로젝트에서 사용되는 이메일 설정은 네이버의 앱 비밀번호를 사용하며, 해당 비밀번호는 보안 상의 이유로 **직접 설정해야** 합니다.

---

### 설정 방법(MailConfig.java, EmailService.java, EmailEnum)

코드를 수정하거나 설정 파일을 변경하는 방법에 대해 설명합니다.

- MailConfig.java
    
    ```java
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.naver.com");
        mailSender.setPort(465);  // 포트 465 또는 587
        mailSender.setUsername("yourEmail@naver.com");
        mailSender.setPassword("yourPassword");  // 네이버 앱 비밀번호
    
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
    ```
    
    **본인 이메일 및 앱 비밀번호 입력**
    
    - mailSender.setUsername("yourEmail@naver.com");
    - mailSender.setPassword("yourPassword");
- EmailService.java
    
    ```java
    public void send(String subject, String text, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("yourEmail@naver.com");  // 발신자 이메일
            helper.setTo(email);  // 수신자 이메일
            helper.setSubject(subject);
            helper.setText(text);
    
            mailSender.send(message);
            logger.info("메일이 발송되었습니다.");
        } catch (MessagingException e) {
            logger.error(String.format("이메일(%s) 발송 실패: %s", email, e.getMessage()));
        }
    }
    ```
    
    MailConfig의 youEmail과 동일한 이메일 입력
    
    - helper.setFrom(”yourEmail@naver.com");
- EmailEnum
    
    ```java
    public enum EmailEnum {
        EOGHKS("zhfldk7316@naver.com");
    
        private final String address;
    
        EmailEnum(String address) {
            this.address = address;
        }
    
        public String getAddress() {
            return address;
        }
    }
    ```
    
    EOGHKS("zhfldk7316@naver.com") 대신 수신 받고 싶은 이메일 입력, **enum에 기재된 메일로 모두 전송되므로 수신 받고 싶은 이메일을 해당 형식으로 열거**
    

---

### 네이버 이메일 설정

![image.jpg](image/smtp%20setting.jpg)

1. 네이버 메일 → 환경 설정 → POP3/IMAP 설정 → IMAP/SMTP 설정으로 이동
2. IMAP/SMTP 사용 → **사용함** 선택

1. 네이버 메일 → 환경 설정 → POP3/IMAP 설정 → IMAP/SMTP 설정으로 이동
2. IMAP/SMTP 사용 → **사용함** 선택
