package study.ticket.ticket_queue.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
//@AllArgsConstructor
@Data
@Builder
public class TokenPayload {
    private String userId;
    private long showId;
    private Long waitingScore;
    private WaitingQueueStatus status;
}
