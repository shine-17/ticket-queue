package study.ticket.ticket_queue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;
import study.ticket.ticket_queue.port.WaitingQueuePort;
import study.ticket.ticket_queue.redis.util.RedisKeys;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final WaitingQueuePort waitingQueuePort;

    @Override
    public WaitingQueueTokenPayload book(String userId, long showId) {


        /*
            1. active count < capacity 일 경우 active user 등록 후 active token 발급

            2. active count > capacity 일 경우 waiting user 등록 후 waiting token 발급
            3. polling
                3-1. active user 일 경우 active token 발급 (TTL: 5분)
                3-2. waiting user 일 경우 waiting status 응답
         */



        waitingQueuePort.add(userId, showId);


        return null;
    }


}
