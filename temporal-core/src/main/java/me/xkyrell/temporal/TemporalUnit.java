package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;

public enum TemporalUnit {

    MILLIS(1L),
    SECONDS(1000L),
    MINUTES(60_000L),
    HOURS(3_600_000L),
    DAYS(86_400_000L);

    private final long millis;

    TemporalUnit(long millis) {
        this.millis = millis;
    }

    /**
     * Converts the specified amount from the given unit to this unit.
     *
     * @param amount the time amount
     * @param unit the source unit
     * @return the converted amount
     * @throws ArithmeticException if the conversion overflows
     * @since 1.0
     */
    public long convert(long amount, @NotNull TemporalUnit unit) {
        return Math.multiplyExact(amount, unit.millis) / millis;
    }

    /**
     * Returns the millisecond representation of this unit.
     *
     * @return the millisecond count
     * @since 1.0
     */
    public long toMillis() {
        return millis;
    }
}
