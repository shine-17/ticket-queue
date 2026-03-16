package study.ticket.ticket_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;

@AllArgsConstructor
@Getter
public class WaitingQueueResponse {
    private String userId;
    private long position;
    private long totalWaitingCount;

    private WaitingQueueStatus status;
}
