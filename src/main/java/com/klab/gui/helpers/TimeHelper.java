package com.klab.gui.helpers;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeHelper {

    private TimeHelper() {
    }

    public static boolean isTheSameDay(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toDays() == 0 && start.getDayOfWeek().equals(end.getDayOfWeek());
    }
}
