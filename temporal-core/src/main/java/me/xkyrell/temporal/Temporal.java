package me.xkyrell.temporal;

import lombok.*;
import me.xkyrell.temporal.format.style.TemporalStyle;
import me.xkyrell.temporal.format.style.TextualTemporalStyle;
import java.time.Duration;
import java.util.function.Function;

@Getter
@ToString
@EqualsAndHashCode
public class Temporal implements TemporalHolder<Temporal>, TemporalValue, Cloneable {

    public static final Temporal ZERO = new Temporal(0L);

    private long millis;

    private Temporal(long millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        this.millis = millis;
    }

    public static Temporal of(long millis) {
        return new Temporal(millis);
    }

    public static Temporal of(long millis, @NonNull TemporalUnit unit) {
        return new Temporal(millis * unit.toMillis());
    }

    public static Temporal of(@NonNull Duration duration) {
        return new Temporal(duration.toMillis());
    }

    public static Temporal of(@NonNull TemporalValue value) {
        return new Temporal(value.getMillis());
    }

    public static Temporal between(long start, long end) {
        return new Temporal(end - start);
    }

    public static Temporal between(@NonNull TemporalValue start, @NonNull TemporalValue end) {
        return between(start.getMillis(), end.getMillis());
    }

    public static Temporal parse(String value, TemporalStyle style) {
        return new Temporal(style.parse(value));
    }

    public static Temporal parse(String value) {
        return parse(value, TextualTemporalStyle.textual());
    }

    @Override
    public Temporal operation(@NonNull Function<Long, Long> operator) {
        if (millis < 0) {
            throw new IllegalArgumentException("Time cannot be negative");
        }
        millis = operator.apply(millis);
        return this;
    }

    @Override
    public long get(@NonNull TemporalUnit unit) {
        return unit.convert(millis, TemporalUnit.MILLIS);
    }

    @Override
    public int compareTo(@NonNull TemporalValue other) {
        return Long.compare(millis, other.getMillis());
    }

    @SneakyThrows(CloneNotSupportedException.class)
    @Override
    public Temporal clone() {
        return (Temporal) super.clone();
    }

    public Duration toDuration() {
        return Duration.ofMillis(millis).minus(Duration.ZERO);
    }

    public boolean isValid() {
        return (millis > 0);
    }

    public boolean isZero() {
        return (millis == 0L);
    }
}
