package study.ticket.ticket_queue.domain;

import lombok.Data;

@Data
public class WaitingQueueStatusInfo {
    private Long scorePosition;
    private WaitingQueueStatus status;
    private String token;
}
