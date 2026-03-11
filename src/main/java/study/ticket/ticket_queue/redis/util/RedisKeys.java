package study.ticket.ticket_queue.redis.util;

public enum RedisKeys {
    ACTIVE_USER("active:%s"),               // active:{showId}
    WAITING_USER("waiting:%s"),             // waiting:{showId}
    ADMISSION_POINT("admission:%s");        // admission:{showId}

    private final String format;

    RedisKeys(String format) {
        this.format = format;
    }

    public String generateKey(Object... args) {
        return format.formatted(args);
    }
}
