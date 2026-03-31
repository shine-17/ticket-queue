package study.ticket.ticket_queue.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import study.ticket.ticket_queue.domain.WaitingQueueResult;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;
import study.ticket.ticket_queue.port.WaitingQueuePort;
import study.ticket.ticket_queue.util.JsonHelper;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RedisTest {

    @Autowired
    private WaitingQueuePort waitingQueuePort;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("Sorted Set에 값 추가하기")
    void zAdd() {
        String userId = "test3";
        long showId = 1L;

//        waitingQueuePort.enqueue(userId, showId);
    }

    @Test
    @DisplayName("대기열 신규 등록")
    void zCard() {
        String userId = "test3";
        long showId = 1L;

        WaitingQueueResult enqueue = waitingQueuePort.enqueue(userId, showId);
        assertThat(enqueue.getStatus()).isEqualByComparingTo(WaitingQueueStatus.WAITING);
    }
}
