package study.ticket.ticket_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;

@AllArgsConstructor
@Getter
public class WaitingQueueResponse {
    private Long waitingScore;
    private WaitingQueueStatus status;
    private String token;
}
