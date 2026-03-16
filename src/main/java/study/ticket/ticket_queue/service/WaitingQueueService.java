package study.ticket.ticket_queue.service;

import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;

public interface WaitingQueueService {
    TokenPayload add(String userId, long showId, long waitingScore);

    WaitingQueueStatusInfo enterQueue(final WaitingQueueRequest request);
    WaitingQueueStatusInfo getQueueStatus();
}
