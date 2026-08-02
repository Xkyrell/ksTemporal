package me.xkyrell.temporal;

import lombok.NonNull;
import me.xkyrell.temporal.format.style.TemporalStyle;
import me.xkyrell.temporal.format.style.TextualTemporalStyle;

public interface TemporalValue extends Comparable<TemporalValue> {

    long get(@NonNull TemporalUnit unit);

    long getMillis();

    default boolean isGreaterThan(@NonNull TemporalValue value) {
        return compareTo(value) > 0;
    }

    default boolean isLessThan(@NonNull TemporalValue value) {
        return compareTo(value) < 0;
    }

    default boolean isEqual(@NonNull TemporalValue value) {
        return compareTo(value) == 0;
    }

    default String formatAs(TemporalStyle style) {
        return style.format(getMillis());
    }

    default String format() {
        return formatAs(TextualTemporalStyle.textual());
    }
}
