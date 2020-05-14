package org.juhewo.mail.spring.boot.repository;

import lombok.extern.slf4j.Slf4j;
import org.juhewu.mail.MailAccount;
import org.juhewu.mail.MailAccountRepository;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * reids中的邮箱账户
 *
 * @author duanjw
 */
@Component
@Slf4j
public class RedisEmailAccountRepository implements MailAccountRepository {
    /**
     * 邮箱账户redis中的key
     */
    private final String emailAccountKey = "email::accounts::key";
    private RedisTemplate redisTemplate;

    public RedisEmailAccountRepository(RedisConnectionFactory redisConnectionFactory) {
        redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        final Jackson2JsonRedisSerializer<MailAccount> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(MailAccount.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        // 支持JAVA8时间格式
//        objectMapper.registerModule(new ParameterNamesModule())
//                .registerModule(new Jdk8Module())
//                .registerModule(new JavaTimeModule());
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(redisTemplate.getStringSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
    }

    /**
     * 所有邮箱账户
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
     * 根据邮箱账户id获取邮箱账户
     *
     * @param id
     * @return
     */
    @Override
    public MailAccount getMailAccount(String id) {
        return (MailAccount) redisTemplate.opsForHash().get(emailAccountKey, id);
    }

    /**
     * 新增邮箱账户
     *
     * @param emailAccount
     */
    @Override
    public void add(MailAccount emailAccount) {
        redisTemplate.opsForHash().put(emailAccountKey, emailAccount.getId(), emailAccount);
    }

    /**
     * 批量新增邮箱账户
     *
     * @param emailAccounts
     */
    @Override
    public void add(List<MailAccount> emailAccounts) {
        redisTemplate.opsForHash().putAll(emailAccountKey, emailAccounts.stream().collect(Collectors.toMap(MailAccount::getId, Function.identity())));
    }

    /**
     * 根据id删除邮箱账户
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        redisTemplate.opsForHash().delete(emailAccountKey, id);
    }

    @Override
    public void delete(List<String> ids) {
        redisTemplate.opsForHash().delete(emailAccountKey, ids);
    }


}
