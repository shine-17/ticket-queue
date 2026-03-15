package study.ticket.ticket_queue.service;

import study.ticket.ticket_queue.domain.TokenPayload;

public interface QueueService {
    TokenPayload add(String userId, long showId, long waitingScore);

//    enterQueue
//    getQueueStatus
}
