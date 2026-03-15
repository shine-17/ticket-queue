package study.ticket.ticket_queue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.ticket.ticket_queue.domain.TokenPayload;
import study.ticket.ticket_queue.domain.QueueStatus;
import study.ticket.ticket_queue.dto.QueueRequest;
import study.ticket.ticket_queue.dto.QueueResponse;
import study.ticket.ticket_queue.service.QueueService;

@RestController
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @PostMapping("/api/ticket/enter")
    public QueueResponse enterQueue(QueueRequest request) {


        return null;
    }

    // polling
    @GetMapping("/api/ticket/status")
    public QueueResponse waiting(QueueRequest request, HttpServletResponse response) {

        // jwt의 payload 데이터 변환
        long waitingScore = 0;

        TokenPayload responsePayload = queueService.add(request.getUserId(), request.getShowId(), waitingScore);

        if (responsePayload.getStatus().compareTo(QueueStatus.ACTIVE) == 0) {
            // access token
//            Cookie cookie = new Cookie("accessToken", payload.getToken().accessToken());
//            response.addCookie(cookie);


            // header? body?
//            response.setHeader("Authorization", "Bearer " + responsePayload.getToken().accessToken());
        }


        return null;
    }

}
