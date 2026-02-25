package study.ticket.ticket_queue.redis.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public enum RedisKeys {
    BOOKED_SEAT("show:%s:seat:booked:%s"),
    PREEMPTED_SEAT("show:%s:seat:preempted:%s"),
    BOOKING("booked:%s"),

    ACTIVE_USER("active:%s:%s:%s"),
    WAITING_USER("waiting:%s:%s:%s"); // waiting:{showId}:{UUID}:{Timestamp}

    private final String format;

    RedisKeys(String format) {
        this.format = format;
    }

    public String generateKey(Long showId) {
        return format.formatted(showId, UUID.randomUUID(), LocalDateTime.now());
    }

    public String generateKey(Long showId, Long seatId) {
        return format.formatted(showId, seatId);
    }

    public List<String> generateKeys(Long showId, List<Long> seatIds) {
        List<String> keys = new ArrayList<>();
        seatIds.forEach(seatId -> keys.add(generateKey(showId, seatId)));
        return keys;
    }
}
