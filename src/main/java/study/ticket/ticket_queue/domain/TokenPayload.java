package study.ticket.ticket_queue.domain;

import lombok.Data;
import lombok.Getter;
import study.ticket.ticket_queue.dto.WaitingQueueStatus;

@Getter
//@AllArgsConstructor
@Data
public class TokenPayload {

//    private String uuid;
    private String userId;
    private long scorePosition;
    private long totalWaitingCount;

    private WaitingQueueStatus status;
//    private JwtToken token;

//    private long showId;

//    private long order;
//    private String type; // ActiveToken or WaitingToken


}
