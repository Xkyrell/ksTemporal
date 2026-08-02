package me.xkyrell.temporal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TemporalUnit {

    MILLIS(1L),
    SECONDS(1000L),
    MINUTES(60_000L),
    HOURS(3_600_000L),
    DAYS(86_400_000L),
    MONTHS(2_592_000_000L),
    YEARS(31_536_000_000L),
    FOREVER(Long.MAX_VALUE);

    private final long millis;

    public long convert(long value, TemporalUnit unit) {
        if (this == FOREVER || unit == FOREVER) {
            return Long.MAX_VALUE;
        }
        return value * unit.millis / this.millis;
    }

    public long toMillis() {
        return millis;
    }
}
