package com.example.scheduler;

import com.example.service.EmailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class CrawlingJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(CrawlingJob.class);
    private static final String TARGET_URL = "https://with-you.kr/day.php?fs_area=&fs_hotel=7&frdate=&todate=&stx=%EC%97%B0%ED%9A%8C";
    private static int lastCount = 0;

    @Autowired
    private EmailService emailService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            Document doc = Jsoup.connect(TARGET_URL).get();
            Elements rows = doc.select("table tbody tr");
            int currentCount = rows.size();

            logger.info("🔍 현재 공고 수: %d, 이전: %d\n", currentCount, lastCount);

            if (lastCount != -1 && currentCount > lastCount) {
                emailService.multiSend(
                        "공고 수 증가 알림",
                        String.format("공고 수가 %d개에서 %d개로 증가했습니다.\n확인하세요: %s", lastCount, currentCount, TARGET_URL)
                );
                logger.info("📧 이메일 발송됨!");
            }

            lastCount = currentCount;
        } catch (Exception e) {
            logger.error("❌ 크롤링 실패: " + e.getMessage());
        }
    }
}
