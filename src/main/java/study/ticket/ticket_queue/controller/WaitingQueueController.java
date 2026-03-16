package study.ticket.ticket_queue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;
import study.ticket.ticket_queue.dto.WaitingQueueResponse;
import study.ticket.ticket_queue.service.WaitingQueueService;

@RestController
@RequiredArgsConstructor
public class WaitingQueueController {

    private final WaitingQueueService waitingQueueService;

    @PostMapping("/api/ticket/enter")
    public WaitingQueueResponse enterQueue(WaitingQueueRequest request) {

        WaitingQueueStatusInfo waitingQueueStatusInfo = waitingQueueService.enterQueue(request);

        return null;
    }

    // polling
    @GetMapping("/api/ticket/status")
    public WaitingQueueResponse waiting(WaitingQueueRequest request, HttpServletResponse response) {

        // jwt의 payload 데이터 변환
        long waitingScore = 0;

        TokenPayload responsePayload = waitingQueueService.add(request.getUserId(), request.getShowId(), waitingScore);

        if (responsePayload.getStatus().compareTo(WaitingQueueStatus.ACTIVE) == 0) {
            // access token
//            Cookie cookie = new Cookie("accessToken", payload.getToken().accessToken());
//            response.addCookie(cookie);


            // header? body?
//            response.setHeader("Authorization", "Bearer " + responsePayload.getToken().accessToken());
        }


        return null;
    }

}
