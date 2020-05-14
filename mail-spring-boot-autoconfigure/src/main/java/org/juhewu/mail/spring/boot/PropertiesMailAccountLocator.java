package org.juhewu.mail.spring.boot;


import org.juhewu.mail.MailAccount;
import org.juhewu.mail.MailAccountLocator;

import java.util.List;

/**
 * 配置文件中的邮箱账户
 *
 * @author duanjw
 */
public class PropertiesMailAccountLocator implements MailAccountLocator {
    private MailProperties mailProperties;

    public PropertiesMailAccountLocator(MailProperties mailProperties) {
        this.mailProperties = mailProperties;
    }

    /**
     * 所有邮箱账户
     *
     * @return
     */
    @Override
    public List<MailAccount> getMailAccounts() {
        return mailProperties.getAccounts();
    }

    /**
     * 根据邮箱账户id获取邮箱账户
     *
     * @param id
     * @return
     */
    @Override
    public MailAccount getMailAccount(String id) {
        return mailProperties.getAccounts().stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
    }
}
