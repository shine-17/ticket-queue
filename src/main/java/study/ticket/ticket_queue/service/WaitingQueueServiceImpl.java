package study.ticket.ticket_queue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.ticket.ticket_queue.domain.WaitingQueueResult;
import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;
import study.ticket.ticket_queue.port.WaitingQueuePort;
import study.ticket.ticket_queue.token.JwtProvider;

@Service
@RequiredArgsConstructor
public class WaitingQueueServiceImpl implements WaitingQueueService {

    private final WaitingQueuePort waitingQueuePort;
    private final JwtProvider jwtProvider;

    @Override
    public WaitingQueueStatusInfo enter(final WaitingQueueRequest request) {

        // 대기열에 추가
        WaitingQueueResult enqueueResult = waitingQueuePort.enqueue(request.getUserId(), request.getShowId());

        TokenPayload payload = TokenPayload.builder()
                .userId(request.getUserId())
                .showId(request.getShowId())
                .waitingScore(enqueueResult.getWaitingScore())
                .status(enqueueResult.getStatus())
                .build();

        // token 생성
        String token = jwtProvider.generateToken(payload);

        return WaitingQueueStatusInfo.builder()
                .waitingScore(enqueueResult.getWaitingScore())
                .status(enqueueResult.getStatus())
                .token(token)
                .build();
    }

    @Override
    public WaitingQueueStatusInfo getStatus() {
        return null;
    }


}
