package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.serializer.util.*;
import java.util.Map;

final class TextualTemporalParser {

    private static final int NEGATIVE_SIGN = 1;

    private final TemporalUnit[] units;

    TextualTemporalParser(TemporalUnit[] units) {
        this.units = units;
    }

    Temporal parse(String input, TextualTemporalFormat format) {
        boolean negative = input.startsWith("-");
        String remaining = negative ? input.substring(NEGATIVE_SIGN) : input;
        State state = new State(remaining, format);
        while (state.size < state.parsedUnits.length) {
            if (!tryReadNext(state)) break;
        }

        for (int i = 0; i < state.size - 1; i++) {
            String expected = state.onDelimiter.apply(
                    state.parsedUnits[i], i, state.size
            );

            if (!expected.equals(state.separators[i])) {
                throw new IllegalArgumentException(String.format(
                        "missing delimiter '%s' after unit %s",
                        expected, state.parsedUnits[i]
                ));
            }
        }

        long totalMillis = calculateAmount(state);
        return Temporal.of(negative ? Math.negateExact(totalMillis) : totalMillis);
    }

    private boolean tryReadNext(State state) {
        long value = readValue(state);
        TemporalUnit unit = readUnit(state, value);
        state.parsedUnits[state.size] = unit;
        state.parsedValues[state.size] = value;

        int digitIndex = findFirstDigit(state.remaining);
        if (digitIndex < 0) {
            if (!state.remaining.isEmpty()) {
                throw new IllegalArgumentException(String.format(
                        "unexpected remaining text '%s'", state.remaining
                ));
            }
            state.size++;
            return false;
        }

        state.separators[state.size++] = state.remaining.substring(0, digitIndex);
        state.currentOrdinal = unit.ordinal() - 1;
        if (state.currentOrdinal < state.lastOrdinal) {
            throw new IllegalArgumentException(String.format(
                    "unexpected input after unit %s", unit
            ));
        }

        state.remaining = state.remaining.substring(digitIndex);
        return true;
    }

    private long readValue(State state) {
        int end = 0;
        while (end < state.remaining.length() && isDigit(state.remaining, end)) {
            end++;
        }

        String token = state.remaining.substring(0, end);
        state.remaining = state.remaining.substring(end);
        try {
            return Long.parseLong(token);
        }
        catch (NumberFormatException __) {
            throw new IllegalArgumentException(String.format(
                    "value '%s' is not a number", token
            ));
        }
    }

    private TemporalUnit readUnit(State state, long value) {
        int start = skipWhitespace(state.remaining);
        if (start == state.remaining.length()) {
            throw new IllegalArgumentException(String.format(
                    "missing unit after value '%d'", value
            ));
        }

        String input = state.remaining.substring(start);
        for (int ordinal = state.currentOrdinal; ordinal >= state.lastOrdinal; ordinal--) {
            TemporalUnit unit = units[ordinal];
            NamedUnitProvider provider = state.providers.get(unit);
            if (provider == null) continue;

            String name = provider.provide(value);
            if (!input.startsWith(name)) continue;

            state.remaining = input.substring(name.length());
            return unit;
        }

        throw new IllegalArgumentException(String.format(
                "unknown unit after value '%d'", value
        ));
    }

    private long calculateAmount(State state) {
        long total = 0L;
        for (int i = 0; i < state.size; i++) {
            long value = state.parsedValues[i];
            TemporalUnit unit = state.parsedUnits[i];
            long millis = Math.multiplyExact(value, unit.toMillis());
            total = Math.addExact(total, millis);
        }

        return total;
    }

    private int skipWhitespace(String text) {
        int index = 0;
        while (index < text.length() && Character.isWhitespace(text.charAt(index))) {
            index++;
        }
        return index;
    }

    private int findFirstDigit(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (isDigit(text, i)) return i;
        }
        return -1;
    }

    private boolean isDigit(String text, int index) {
        char symbol = text.charAt(index);
        return symbol >= '0' && symbol <= '9';
    }

    private static final class State {

        private final int lastOrdinal;
        private final Map<TemporalUnit, NamedUnitProvider> providers;
        private final IndexedFunction<TemporalUnit, String> onDelimiter;
        private final TemporalUnit[] parsedUnits;
        private final long[] parsedValues;
        private final String[] separators;
        private int currentOrdinal;
        private int size;
        private String remaining;

        private State(String remaining, TextualTemporalFormat format) {
            this.remaining = remaining;
            this.onDelimiter = format::getDelimiter;
            this.lastOrdinal = format.getLastUnit().ordinal();
            this.currentOrdinal = format.getFirstUnit().ordinal();
            this.providers = format.getNamedUnitProviders();

            int capacity = currentOrdinal - lastOrdinal + 1;
            this.parsedUnits = new TemporalUnit[capacity];
            this.parsedValues = new long[capacity];
            this.separators = new String[capacity];
        }
    }
}