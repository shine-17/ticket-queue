package study.ticket.ticket_queue.service;

import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;

public interface WaitingService {
    WaitingQueueTokenPayload add(String userId, long showId, long waitingScore);
}
