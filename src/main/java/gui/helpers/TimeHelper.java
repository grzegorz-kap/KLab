package gui.helpers;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeHelper {

    public boolean isTheSameDay(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toDays() == 0 && start.getDayOfWeek().equals(end.getDayOfWeek());
    }
}
