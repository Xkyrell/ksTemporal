package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import java.util.function.IntToLongFunction;

final class CompactTemporalFormatter {

    private static final int DEFAULT_UNIT_WIDTH = 2;

    private final TemporalUnit[] units;
    private final IntToLongFunction onMaxValueResolver;

    CompactTemporalFormatter(TemporalUnit[] units, IntToLongFunction onMaxValueResolver) {
        this.units = units;
        this.onMaxValueResolver = onMaxValueResolver;
    }

    String format(Temporal temporal, CompactTemporalFormat format) {
        StringBuilder result = new StringBuilder();
        long remaining = temporal.get(TemporalUnit.MILLIS);
        if (temporal.isNegative()) {
            remaining = -remaining;
            result.append('-');
        }

        int firstOrdinal = format.getFirstUnit().ordinal();
        int lastOrdinal = format.getLastUnit().ordinal();
        for (int ordinal = firstOrdinal; ordinal >= lastOrdinal; ordinal--) {
            TemporalUnit unit = units[ordinal];
            long unitMillis = unit.toMillis();
            long value = remaining / unitMillis;
            if (ordinal > lastOrdinal) {
                remaining %= unitMillis;
            }

            if (format.hasLeadingZero()) {
                int width = getWidth(ordinal, firstOrdinal);
                String valueText = Long.toString(value);
                for (int index = valueText.length(); index < width; index++) {
                    result.append('0');
                }

                result.append(valueText);
            }
            else {
                result.append(value);
            }

            if (ordinal > lastOrdinal) {
                result.append(format.getDelimiter(unit));
            }
        }

        return result.toString();
    }

    private int getWidth(int ordinal, int firstOrdinal) {
        if (ordinal == firstOrdinal) {
            return DEFAULT_UNIT_WIDTH;
        }

        long maxValue = onMaxValueResolver.applyAsLong(ordinal);
        return Long.toString(maxValue).length();
    }
}
