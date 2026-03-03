package study.ticket.ticket_queue.service;

import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;

public interface BookingService {
    WaitingQueueTokenPayload book(String userId, long showId);
}
