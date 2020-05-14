package org.juhewu.mail.spring.boot.send;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.juhewu.mail.MailAccount;
import org.juhewu.mail.MailAccountLocator;
import org.juhewu.mail.MailAccountRepository;
import org.juhewu.mail.MailSenders;
import org.juhewu.mail.sender.MailSender;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author duanjw
 */
@Slf4j
@AllArgsConstructor
@EnableAutoConfiguration
public class MailSendApplication {

    private final MailAccountLocator mailAccountLocator;
    private final MailAccountRepository mailAccountRepository;
    private final MailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(MailSendApplication.class, args);
    }
    @Bean
    public ApplicationRunner runner() {
        initMailAccount();
        MailAccount mailAccount = mailAccountLocator.getMailAccount("1");
        // 发送邮件
        mailSender.send(mailAccount);

        return args -> {
            log.info("发送邮件成功，邮件账户：{}", mailAccount);
        };
    }

    /**
     * 初始化邮件账户
     *
     */
    private void initMailAccount() {
        mailAccountRepository.add(new MailAccount().setId("1"));
    }

}
