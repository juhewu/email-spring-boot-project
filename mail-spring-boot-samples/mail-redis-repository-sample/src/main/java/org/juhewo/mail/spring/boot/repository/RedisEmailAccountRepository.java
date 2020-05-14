package org.juhewo.mail.spring.boot.repository;

import lombok.extern.slf4j.Slf4j;
import org.juhewu.mail.MailAccount;
import org.juhewu.mail.MailAccountRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 账户存储到Redis
 * 替换默认的内存存储，适和用于集群环境
 *
 * @author duanjw
 * @see org.juhewu.mail.InMemoryMailAccountRepository 内存存储
 */
@Slf4j
public class RedisEmailAccountRepository implements MailAccountRepository {
    /**
     * 邮件账户redis中的key
     */
    private final String emailAccountKey = "email::accounts::key";
    private RedisTemplate redisTemplate;

    public RedisEmailAccountRepository(RedisConnectionFactory redisConnectionFactory) {
        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        final Jackson2JsonRedisSerializer<MailAccount> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(MailAccount.class);
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
    }

    /**
     * 所有邮件账户
     *
     * @return
     */
    @Override
    public List<MailAccount> getMailAccounts() {
        List<MailAccount> values = redisTemplate.opsForHash().values(emailAccountKey);
        if (log.isDebugEnabled()) {
            log.debug("Redis中的邮件账户：{}", values);
        }
        return values;
    }

    /**
     * 根据邮件账户id获取邮件账户
     *
     * @param id
     * @return
     */
    @Override
    public MailAccount getMailAccount(String id) {
        return (MailAccount) redisTemplate.opsForHash().get(emailAccountKey, id);
    }

    /**
     * 新增邮件账户
     *
     * @param emailAccount
     */
    @Override
    public void add(MailAccount emailAccount) {
        redisTemplate.opsForHash().put(emailAccountKey, emailAccount.getId(), emailAccount);
    }

    /**
     * 批量新增邮件账户
     *
     * @param emailAccounts
     */
    @Override
    public void add(List<MailAccount> emailAccounts) {
        redisTemplate.opsForHash().putAll(emailAccountKey, emailAccounts.stream().collect(Collectors.toMap(MailAccount::getId, Function.identity())));
    }

    /**
     * 根据id删除邮件账户
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        redisTemplate.opsForHash().delete(emailAccountKey, id);
    }

    /**
     * 根据ids批量删除邮件账户
     *
     * @param ids
     */
    @Override
    public void delete(List<String> ids) {
        redisTemplate.opsForHash().delete(emailAccountKey, ids);
    }


}
