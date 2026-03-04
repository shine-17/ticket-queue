package study.ticket.ticket_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WaitingQueueStatusResponse {
    private String userId;
    private long position;
    private long totalWaitingCount;

    private WaitingQueueStatus status;
}
