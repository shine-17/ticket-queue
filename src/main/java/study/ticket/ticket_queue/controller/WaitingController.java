package study.ticket.ticket_queue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;
import study.ticket.ticket_queue.dto.WaitingQueueStatus;
import study.ticket.ticket_queue.dto.WaitingQueueStatusRequest;
import study.ticket.ticket_queue.dto.WaitingQueueStatusResponse;
import study.ticket.ticket_queue.service.WaitingService;

@RestController
@RequiredArgsConstructor
public class WaitingController {

    private final WaitingService waitingService;

    @PostMapping("/api/ticket/waiting")
    public WaitingQueueStatusResponse waiting(WaitingQueueStatusRequest request, HttpServletResponse response,
                                           @AuthenticationPrincipal WaitingQueueTokenPayload requestPayload) {

        // jwt의 payload 데이터 변환
        long waitingScore = requestPayload.getPosition();

        WaitingQueueTokenPayload responsePayload = waitingService.add(request.getUserId(), request.getShowId(), waitingScore);

        if (responsePayload.getStatus().compareTo(WaitingQueueStatus.ACTIVE) == 0) {
            // access token
//            Cookie cookie = new Cookie("accessToken", payload.getToken().accessToken());
//            response.addCookie(cookie);


            response.setHeader("Authorization", "Bearer " + responsePayload.getToken().accessToken());
        }


        return null;
    }

}
