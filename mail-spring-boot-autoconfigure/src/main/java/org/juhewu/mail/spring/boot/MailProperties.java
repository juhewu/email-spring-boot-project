package org.juhewu.mail.spring.boot;

import lombok.Data;
import org.juhewu.mail.MailAccount;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱配置文件
 *
 * @author duanjw
 */
@Data
@ConfigurationProperties(prefix = "mail")
public class MailProperties {
    private List<MailAccount> accounts = new ArrayList<>();
}
