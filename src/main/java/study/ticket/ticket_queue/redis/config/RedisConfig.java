package study.ticket.ticket_queue.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // Key: String
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Value: Plain string (따옴표 없이 저장)
        GenericToStringSerializer<Object> serializer = new GenericToStringSerializer<>(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        template.setValueSerializer(serializer);
        template.setHashValueSerializer(serializer);

        // execute() 시 전달하는 args(인자)들에 적용되는 직렬화 도구
//        template.setStringSerializer(stringRedisSerializer);

        return template;
    }

//    @Bean
//    public RedisTemplate<String, RedisBookingDto> bookingRedisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, RedisBookingDto> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//
//        // Key: String
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//
//        // Value: Json
//        Jackson2JsonRedisSerializer<RedisBookingDto> serializer = new Jackson2JsonRedisSerializer<>(RedisBookingDto.class);
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);
//
//        return template;
//    }
}
