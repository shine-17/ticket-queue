package study.ticket.ticket_queue.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.ticket.ticket_queue.domain.WaitingQueueStatusInfo;
import study.ticket.ticket_queue.domain.WaitingQueueStatus;
import study.ticket.ticket_queue.dto.WaitingQueueRequest;
import study.ticket.ticket_queue.dto.WaitingQueueResponse;
import study.ticket.ticket_queue.service.WaitingQueueService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WaitingQueueController {

    private final WaitingQueueService waitingQueueService;

    @PostMapping("/api/ticket/enter")
    public WaitingQueueStatusInfo enterQueue(WaitingQueueRequest request) {
        log.info("/api/ticket/enter");

        return waitingQueueService.enter(request);
    }

    // polling
    @GetMapping("/api/ticket/status")
    public WaitingQueueResponse waiting(WaitingQueueRequest request, HttpServletResponse response) {

        // jwt의 payload 데이터 변환
        WaitingQueueStatusInfo waitingQueueStatusInfo = waitingQueueService.enter(request);

        if (waitingQueueStatusInfo.getStatus().compareTo(WaitingQueueStatus.ACTIVE) == 0) {
            // access token
//            Cookie cookie = new Cookie("accessToken", payload.getToken().accessToken());
//            response.addCookie(cookie);


            // header? body?
//            response.setHeader("Authorization", "Bearer " + responsePayload.getToken().accessToken());
        }


        return null;
    }

}
