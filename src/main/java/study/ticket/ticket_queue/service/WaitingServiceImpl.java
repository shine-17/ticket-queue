package study.ticket.ticket_queue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;
import study.ticket.ticket_queue.port.WaitingQueuePort;

@Service
@RequiredArgsConstructor
public class WaitingServiceImpl implements WaitingService {

    private final WaitingQueuePort waitingQueuePort;

    @Override
    public WaitingQueueTokenPayload add(String userId, long showId, long waitingScore) {
        waitingQueuePort.add(userId, showId, waitingScore);
        return null;
    }


}
