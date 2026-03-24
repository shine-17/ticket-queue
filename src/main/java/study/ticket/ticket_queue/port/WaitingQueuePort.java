package study.ticket.ticket_queue.port;

import study.ticket.ticket_queue.domain.WaitingQueueResult;

public interface WaitingQueuePort {
    WaitingQueueResult enqueue(String userId, long showId);
    long findRank(String userId, long showId);
}
