package study.ticket.ticket_queue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;
import study.ticket.ticket_queue.port.WaitingQueuePort;

@Service
@RequiredArgsConstructor
public class WaitingQueueServiceImpl implements WaitingQueueService {

    private final WaitingQueuePort waitingQueuePort;

    @Override
    public TokenPayload add(String userId, long showId, long waitingScore) {
        waitingQueuePort.enqueue(userId, showId, waitingScore);
        return null;
    }

    @Override
    public WaitingQueueStatusInfo enterQueue(final WaitingQueueRequest request) {

        // token 생성




        // token 발급


        return null;
    }

    @Override
    public WaitingQueueStatusInfo getQueueStatus() {
        return null;
    }


}
