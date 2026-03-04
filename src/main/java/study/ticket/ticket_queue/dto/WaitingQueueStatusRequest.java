package study.ticket.ticket_queue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WaitingQueueStatusRequest {
    private String userId;
    private long showId;
}
