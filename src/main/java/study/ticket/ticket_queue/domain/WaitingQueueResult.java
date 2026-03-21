package study.ticket.ticket_queue.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WaitingQueueResult {
    private WaitingQueueStatus status;
    private Long waitingScore;

    public boolean isActive() {
        return status == WaitingQueueStatus.ACTIVE;
    }

    public boolean isWaiting() {
        return status == WaitingQueueStatus.WAITING;
    }
}
