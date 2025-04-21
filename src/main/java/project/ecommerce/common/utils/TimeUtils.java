package project.ecommerce.common.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String format(LocalDateTime timestamp) {
        return timestamp.format(FORMATTER);
    }

    public static String timeAgo(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(timestamp, now);
        long seconds = duration.getSeconds();

        if (seconds < 60) return "방금 전";
        if (seconds < 3600) return seconds / 60 + "분 전";
        if (seconds < 86400) return seconds / 3600 + "시간 전";
        if (seconds < 2592000) return seconds / 86400 + "일 전";
        if (seconds < 31536000) return seconds / 2592000 + "달 전";
        return seconds / 31536000 + "년 전";
    }
}

