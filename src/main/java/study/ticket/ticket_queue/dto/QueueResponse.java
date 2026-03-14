package study.ticket.ticket_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.ticket.ticket_queue.domain.QueueStatus;

@AllArgsConstructor
@Getter
public class QueueResponse {
    private String userId;
    private long position;
    private long totalWaitingCount;

    private QueueStatus status;
}
