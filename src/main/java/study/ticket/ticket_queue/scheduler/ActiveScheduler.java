package study.ticket.ticket_queue.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ActiveScheduler {

    // Waiting User -> Activer User
    @Scheduled
    public void activateUser() {

    }

    // Admission:{showId} update
//    @Scheduled(cron = "* * * * * *")
    @Scheduled(initialDelay = 500, fixedDelay = 500)
    public void updateAdmissionPoint() {
        // TTL 만료 관리: ZREMRANGEBYSCORE active:{showId} -inf now

        // admission point 업데이트: current admission + (capacity - active user count)
        // active queue의 빈 자리만큼 더해서 업데이트

        // ZCARD: zset 사이즈 확인


        // admission point: local cache 고려
    }

}
