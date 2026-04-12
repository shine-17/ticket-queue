package study.ticket.ticket_queue.redis.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import study.ticket.ticket_queue.domain.WaitingQueueResult;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;
import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.port.WaitingQueuePort;
import study.ticket.ticket_queue.redis.util.RedisKeys;
import study.ticket.ticket_queue.util.JsonHelper;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisWaitingQueue implements WaitingQueuePort {

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${ticket.booking.capacity}")
    private int CAPACITY;

    @Value("${active.user.ttl}")
    private int ACTIVE_USER_TTL;

    @Override
    public WaitingQueueResult enqueue(String userId, long showId) {

        // waiting queue score에 sequence 넣기


        // Lua Script
        RedisScript<List> script = getLuaScript("script/redis/enqueue.lua", List.class);

        // key[1]: active key, key[2]: waiting key
        List<String> keys = getKeys(userId, showId);

        LocalDateTime now = LocalDateTime.now();
        long currentTime = Timestamp.valueOf(now).getTime() * 1000;
        long expireTime = Timestamp.valueOf(now.plusMinutes(ACTIVE_USER_TTL)).getTime();

        // ARGV: userId, seatCount, ttl
        List<Object> result = redisTemplate.execute(
                script,
                keys,
                userId,
                CAPACITY,
                currentTime,
                expireTime
        );


        if (result == null) {
            throw new IllegalStateException("Redis operations failed to add user \"" + userId + "\" to queue");
        }

        String status = (String) result.get(0);
        long position = (long) result.get(1);

        return WaitingQueueResult.builder()
                .status(WaitingQueueStatus.valueOf(status))
                .waitingScore(position)
                .build();
    }

    @Override
    public long findRank(String userId, long showId) {
        // 인덱스 0부터 시작
        return redisTemplate.opsForZSet().rank(RedisKeys.WAITING_USER.generateKey(showId), userId) + 1;
//        Object obj = redisTemplate.opsForValue().get(RedisKeys.ADMISSION_POINT.generateKey(showId));
//        if
    }

    private void issueToken(String userId, long showId) {
        // Lua Script
        RedisScript<String> script = getLuaScript("script/redis/issue_token.lua", String.class);

        // key[1]: active key, key[2]: waiting key
        List<String> keys = getKeys(userId, showId);

        LocalDateTime now = LocalDateTime.now();
        long currentTime = Timestamp.valueOf(now).getTime();
        long expireTime = Timestamp.valueOf(now.plusMinutes(ACTIVE_USER_TTL)).getTime();

        // ARGV: userId, seatCount, ttl
        String result = redisTemplate.execute(
                script,
                keys,
                userId,
                CAPACITY,
                currentTime,
                expireTime
        );


        if (result == null) {
            throw new IllegalStateException("");
        }
        else if (result.equals("ALREADY_ACTIVE")) {
            throw new IllegalStateException("이미 예약된 좌석입니다.");
        }
        else if (result.equals("ALREADY_WAITING")) {
            throw new IllegalStateException("이미 선점된 좌석입니다.");
        }
        else if (result.equals("ADD_ACTIVE")) {

        }
        else if (result.equals("ADD_WAITING")) {

        }
        else if (result.equals("DUPLICATION")) {

        }

        // result == 1이면 성공
    }

    private <T> RedisScript<T> getLuaScript(String resourcePath, Class<T> resultType) {
        DefaultRedisScript<T> script = new DefaultRedisScript<>();
        script.setLocation(new ClassPathResource(resourcePath));
        script.setResultType(resultType);
        return script;
    }

    private List<String> getKeys(String userId, long showId) {
        // active key, waiting key
        return List.of(
                RedisKeys.ACTIVE_USER.generateKey(showId),
                RedisKeys.WAITING_USER.generateKey(showId),
//                RedisKeys.ACTIVE_SESSION.generateKey(showId, userId),
                RedisKeys.ADMISSION_POINT.generateKey(showId)
        );
    }
}
