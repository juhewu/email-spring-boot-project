package org.juhewu.mail.spring.boot;

import org.juhewu.mail.CompositeMailAccountLocator;
import org.juhewu.mail.InMemoryMailAccountRepository;
import org.juhewu.mail.MailAccountLocator;
import org.juhewu.mail.MailAccountRepository;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;


/**
 * 邮箱自动配置
 *
 * @author duanjw
 */
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableConfigurationProperties({MailProperties.class})
public class MailAutoConfiguration {

    /**
     * 内存中的邮箱账户
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(MailAccountRepository.class)
    public InMemoryMailAccountRepository inMemoryEmailAccountRepository() {
        return new InMemoryMailAccountRepository();
    }


    /**
     * 配置文件中的邮箱账户
     *
     * @param mailProperties
     * @return
     */
    @Bean
    public PropertiesMailAccountLocator propertiesEmailAccountLocator(MailProperties mailProperties) {
        return new PropertiesMailAccountLocator(mailProperties);
    }

    /**
     * 邮箱账户定位器，包括所有的邮箱定位器
     *
     * @param emailAccountLocators
     * @return
     */
    @Bean
    @Primary
    public MailAccountLocator emailAccountLocator(
            List<MailAccountLocator> emailAccountLocators) {
        return new CompositeMailAccountLocator(emailAccountLocators);
    }
}
