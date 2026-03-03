package study.ticket.ticket_queue.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.ticket.ticket_queue.domain.WaitingQueueTokenPayload;
import study.ticket.ticket_queue.dto.WaitingQueueStatus;
import study.ticket.ticket_queue.dto.WaitingQueueStatusRequest;
import study.ticket.ticket_queue.dto.WaitingQueueStatusResponse;
import study.ticket.ticket_queue.service.BookingService;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/api/ticket/book")
    public WaitingQueueStatusResponse book(WaitingQueueStatusRequest request, HttpServletResponse response) {

        WaitingQueueTokenPayload payload = bookingService.book(request.getUserId(), request.getShowId());

        if (payload.getStatus().compareTo(WaitingQueueStatus.ACTIVE) == 0) {
            // access token
            Cookie cookie = new Cookie("accessToken", payload.getToken().accessToken());
            response.addCookie(cookie);
        }


        return null;
    }

}
