package study.ticket.ticket_queue.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public final class JsonHelper {
    private static Gson gson;

    public JsonHelper() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .setPrettyPrinting()
                    .serializeNulls()
                    .disableHtmlEscaping()
                    .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                    .create();
        }
    }

    public static String toJson(final Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
