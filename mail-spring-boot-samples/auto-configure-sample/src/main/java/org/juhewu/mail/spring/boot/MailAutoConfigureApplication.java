package org.juhewu.mail.spring.boot;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.juhewu.mail.MailAccountLocator;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author duanjw
 */
@Slf4j
@AllArgsConstructor
@EnableAutoConfiguration
public class MailAutoConfigureApplication {

    private final MailAccountLocator mailAccountLocator;

    public static void main(String[] args) {
        SpringApplication.run(MailAutoConfigureApplication.class, args);
    }
    @Bean
    public ApplicationRunner runner() {
        return args -> log.info("所有邮件账户：{}",mailAccountLocator.getMailAccounts());
    }
}
