package me.xkyrell.temporal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
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
     * Returns whether this value is a multiple of the specified amount.
     *
     * @param amount the time amount
     * @param unit the unit of the specified amount
     * @return {@code true} if this value is a multiple, otherwise {@code false}
     * @since 2.0
     */
    boolean isMultipleOf(long amount, @NotNull TemporalUnit unit);

    /**
     * Returns whether this value is a multiple of the specified value.
     *
     * @param value the divisor
     * @return {@code true} if this value is a multiple, otherwise {@code false}
     * @since 2.0
     */
    boolean isMultipleOf(@NotNull TemporalValue value);

    /**
     * Returns whether this value is greater than or equal to the specified value.
     *
     * @param value the value to compare to
     * @return {@code true} if this value is greater than or equal to the specified value, otherwise {@code false}
     * @since 2.0
     */
    default boolean isGreaterThanOrEqual(@NotNull TemporalValue value) {
        return compareTo(value) >= 0;
    }

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
     * Returns whether this value is less than or equal to the specified value.
     *
     * @param value the value to compare to
     * @return {@code true} if this value is less than or equal to the specified value, otherwise {@code false}
     * @since 2.0
     */
    default boolean isLessThanOrEqual(@NotNull TemporalValue value) {
        return compareTo(value) <= 0;
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

    /**
     * Returns whether this value is positive.
     *
     * @return {@code true} if this value is positive, otherwise {@code false}
     * @since 2.0
     */
    default boolean isPositive() {
        return get(TemporalUnit.MILLIS) > 0L;
    }

    /**
     * Returns whether this value is negative.
     *
     * @return {@code true} if this value is negative, otherwise {@code false}
     * @since 2.0
     */
    default boolean isNegative() {
        return get(TemporalUnit.MILLIS) < 0L;
    }

    /**
     * Returns whether this value is zero.
     *
     * @return {@code true} if this value is zero, otherwise {@code false}
     * @since 1.0
     */
    default boolean isZero() {
        return get(TemporalUnit.MILLIS) == 0L;
    }
}
