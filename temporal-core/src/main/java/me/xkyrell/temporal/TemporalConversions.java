package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public final class TemporalConversions {

    private TemporalConversions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Duration toDuration(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value");
        return Duration.ofMillis(value.get(TemporalUnit.MILLIS));
    }

    public static Temporal fromDuration(@NotNull Duration duration) {
        Objects.requireNonNull(duration, "duration");
        return Temporal.of(duration.toMillis());
    }

    public static Instant toInstant(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value");
        return Instant.ofEpochMilli(value.get(TemporalUnit.MILLIS));
    }

    public static Temporal from(@NotNull Instant instant) {
        Objects.requireNonNull(instant, "instant");
        return Temporal.of(instant.toEpochMilli(), TemporalUnit.MILLIS);
    }
}
