package me.xkyrell.temporal;

import lombok.NonNull;
import java.util.function.Function;

public interface TemporalHolder<T extends TemporalHolder<T>> {

    T operation(@NonNull Function<Long, Long> operator);

    default T plus(@NonNull TemporalValue value) {
        return operation(millis -> millis + value.getMillis());
    }

    default T minus(@NonNull TemporalValue value) {
        return operation(millis -> millis - value.getMillis());
    }

    default T multiply(int multiplier) {
        return operation(millis -> millis * multiplier);
    }

    default T divide(int divisor) {
        return operation(millis -> millis / divisor);
    }
}
