package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;

public interface TemporalValue extends Comparable<TemporalValue> {

    /**
     * Returns the value in the specified unit.
     *
     * @param unit the unit of the specified amount
     * @return the converted value
     * @since 1.0
     */
    long get(@NotNull TemporalUnit unit);

    /**
     * Returns whether this value is greater than the specified value.
     *
     * @param value the value to compare to
     * @return {@code true} if this value is greater than the specified value, otherwise {@code false}
     * @since 1.0
     */
    default boolean isGreaterThan(@NotNull TemporalValue value) {
        return compareTo(value) > 0;
    }

    /**
     * Returns whether this value is less than the specified value.
     *
     * @param value the value to compare to
     * @return {@code true} if this value is less than the specified value, otherwise {@code false}
     * @since 2.0
     */
    default boolean isLessThan(@NotNull TemporalValue value) {
        return compareTo(value) < 0;
    }
}
