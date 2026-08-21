package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.serializer.util.NamedUnitProvider;
import java.util.Map;

final class TextualTemporalFormatter {

    private final TemporalUnit[] units;

    TextualTemporalFormatter(TemporalUnit[] units) {
        this.units = units;
    }

    String format(Temporal temporal, TextualTemporalFormat format) {
        long remaining = temporal.get(TemporalUnit.MILLIS);
        StringBuilder result = new StringBuilder();
        if (temporal.isNegative()) {
            remaining = -remaining;
            result.append('-');
        }

        int firstOrdinal = format.getFirstUnit().ordinal();
        int lastOrdinal = format.getLastUnit().ordinal();
        int maxParts = firstOrdinal - lastOrdinal + 1;
        TemporalUnit[] partUnits = new TemporalUnit[maxParts];
        long[] partValues = new long[maxParts];
        int size = 0;

        Map<TemporalUnit, NamedUnitProvider> providers = format.getNamedUnitProviders();
        boolean unitSpacing = format.hasUnitSpacing();
        TextualZeroPolicy policy = format.getZeroPolicy();
        for (int ordinal = firstOrdinal; ordinal >= lastOrdinal; ordinal--) {
            TemporalUnit unit = units[ordinal];
            long value = remaining / unit.toMillis();
            if (ordinal > lastOrdinal) {
                remaining %= unit.toMillis();
            }

            boolean hasRemainingAfter = ordinal > lastOrdinal && remaining != 0L;
            if (policy.shouldShowUnit(value, size, hasRemainingAfter)) {
                partUnits[size] = unit;
                partValues[size] = value;
                size++;
            }
        }

        if (size == 0 && policy.shouldSmallestUnit()) {
            result.append(toFormat(providers, format.getLastUnit(), 0L, unitSpacing));
        }

        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(format.getDelimiter(partUnits[i], i, size));
            }
            result.append(toFormat(providers, partUnits[i], partValues[i], unitSpacing));
        }

        return result.toString();
    }

    private String toFormat(
            Map<TemporalUnit, NamedUnitProvider> providers,
            TemporalUnit unit, long value, boolean unitSpacing
    ) {
        NamedUnitProvider provider = providers.get(unit);
        if (provider == null) {
            throw new IllegalStateException("No named unit provider configured for unit " + unit);
        }

        return value + (unitSpacing ? " " : "") + provider.provide(value);
    }
}
