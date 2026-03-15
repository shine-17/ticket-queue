package study.ticket.ticket_queue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.port.WaitingQueuePort;

@Service
@RequiredArgsConstructor
public class QueueServiceImpl implements QueueService {

    private final WaitingQueuePort waitingQueuePort;

    @Override
    public TokenPayload add(String userId, long showId, long waitingScore) {
        waitingQueuePort.add(userId, showId, waitingScore);
        return null;
    }


}
