package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import java.util.function.IntToLongFunction;

final class CompactTemporalParser {

    private static final int NEGATIVE_SIGN = 1;

    private final TemporalUnit[] units;
    private final IntToLongFunction maxValueResolver;

    CompactTemporalParser(TemporalUnit[] units, IntToLongFunction maxValueResolver) {
        this.units = units;
        this.maxValueResolver = maxValueResolver;
    }

    Temporal parse(String input, CompactTemporalFormat format) {
        boolean negative = input.startsWith("-");
        String remaining = negative ? input.substring(NEGATIVE_SIGN) : input;
        int firstOrdinal = format.getFirstUnit().ordinal();
        int lastOrdinal = format.getLastUnit().ordinal();
        long totalMillis = 0L;
        for (int ordinal = firstOrdinal; ordinal >= lastOrdinal; ordinal--) {
            TemporalUnit unit = units[ordinal];
            String token = remaining;
            if (ordinal > lastOrdinal) {
                String delimiter = format.getDelimiter(unit);
                int index = remaining.indexOf(delimiter);
                if (index < 0) {
                    throw new IllegalArgumentException(String.format(
                            "missing delimiter '%s' after unit %s",
                            delimiter, unit
                    ));
                }

                token = remaining.substring(0, index);
                remaining = remaining.substring(index + delimiter.length());
            }

            long value = parseValueInternal(token);
            if (ordinal < firstOrdinal) {
                long maxValue = maxValueResolver.applyAsLong(ordinal);
                if (value > maxValue) {
                    throw new IllegalArgumentException(String.format(
                            "value '%s' for unit %s exceeds maximum value %d",
                            token, unit, maxValue
                    ));
                }
            }

            long valueMillis = Math.multiplyExact(value, unit.toMillis());
            if (negative) {
                valueMillis = Math.negateExact(valueMillis);
            }

            totalMillis = Math.addExact(totalMillis, valueMillis);
        }

        return Temporal.of(totalMillis);
    }

    private long parseValueInternal(String token) {
        try {
            return Long.parseLong(token);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format(
                    "value '%s' is not a number", token
            ), ex);
        }
    }
}
