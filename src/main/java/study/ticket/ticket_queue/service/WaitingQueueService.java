package study.ticket.ticket_queue.service;

import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;

public interface WaitingQueueService {
    WaitingQueueStatusInfo enter(final WaitingQueueRequest request);
    WaitingQueueStatusInfo getStatus();
}
