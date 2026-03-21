package study.ticket.ticket_queue.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class WaitingQueueStatusInfo {
    private Long waitingScore;

    @NonNull
    private WaitingQueueStatus status;

    @NonNull
    private String token;
}
