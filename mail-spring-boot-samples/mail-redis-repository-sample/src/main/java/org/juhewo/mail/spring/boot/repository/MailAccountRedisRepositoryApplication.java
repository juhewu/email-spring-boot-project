package org.juhewo.mail.spring.boot.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.juhewu.mail.MailAccount;
import org.juhewu.mail.MailAccountLocator;
import org.juhewu.mail.MailAccountRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * @author duanjw
 */
@Slf4j
@AllArgsConstructor
@EnableAutoConfiguration
@ComponentScan
public class MailAccountRedisRepositoryApplication {
    
    private final MailAccountLocator mailAccountLocator;
    private final MailAccountRepository mailAccountRepository;

    public static void main(String[] args) {
        SpringApplication.run(MailAccountRedisRepositoryApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        // 初始化邮件账户
        add();
        // 获取所有邮件账户
        List<MailAccount> mailAccounts = mailAccountLocator.getMailAccounts();
        return args -> log.info("所有邮件账户数：{}，账户：{}", mailAccounts.size(), mailAccounts);
    }


    /**
     * 初始化邮件账户
     *
     * @see RedisEmailAccountRepository 账户会存储到redis
     */
    private void add() {
        mailAccountRepository.add(new MailAccount().setId("1"));
    }


}
