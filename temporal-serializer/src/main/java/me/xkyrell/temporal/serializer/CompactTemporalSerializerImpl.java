package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.IntToLongFunction;
import java.util.function.Supplier;

final class CompactTemporalSerializerImpl implements CompactTemporalSerializer {

    private static final TemporalUnit[] UNITS = TemporalUnit.values();
    private static final IntToLongFunction MAX_VALUE_RESOLVER = ordinal -> {
        TemporalUnit unit = UNITS[ordinal];
        TemporalUnit largerUnit = UNITS[ordinal + 1];
        return largerUnit.toMillis() / unit.toMillis() - 1;
    };

    private final CompactTemporalParser parser;
    private final CompactTemporalFormatter formatter;

    private CompactTemporalSerializerImpl() {
        parser = new CompactTemporalParser(UNITS, MAX_VALUE_RESOLVER);
        formatter = new CompactTemporalFormatter(UNITS, MAX_VALUE_RESOLVER);
    }

    @Override
    public Temporal deserialize(@NotNull String input, @NotNull Iterable<? extends CompactTemporalFormat> formats) {
        Objects.requireNonNull(input, "input cannot be null");
        Objects.requireNonNull(formats, "formats cannot be null");

        TemporalSerializationException exception = null;
        for (CompactTemporalFormat format : formats) {
            try {
                return parser.parse(input, format);
            }
            catch (IllegalArgumentException ex) {
                if (exception == null) {
                    exception = new TemporalSerializationException(input);
                }
                exception.addSuppressed(ex);
            }
        }

        throw exception == null
                ? new TemporalSerializationException(input)
                : exception;
    }

    @Override
    public Temporal deserializeOrFallback(@NotNull String input, @NotNull CompactTemporalFormat format, @NotNull Supplier<Temporal> onFallback) {
        Objects.requireNonNull(input, "input cannot be null");
        Objects.requireNonNull(format, "format cannot be null");
        Objects.requireNonNull(onFallback, "onFallback cannot be null");

        try {
            return parser.parse(input, format);
        }
        catch (IllegalArgumentException __) {
            return onFallback.get();
        }
    }

    @Override
    public Temporal deserialize(@NotNull String input, @NotNull CompactTemporalFormat format) {
        Objects.requireNonNull(input, "input cannot be null");
        Objects.requireNonNull(format, "format cannot be null");

        try {
            return parser.parse(input, format);
        }
        catch (IllegalArgumentException ex) {
            throw new TemporalSerializationException(input, ex);
        }
    }

    @Override
    public String serializeOrFallback(@NotNull Temporal temporal, @NotNull CompactTemporalFormat format, @NotNull Supplier<String> onFallback) {
        Objects.requireNonNull(temporal, "temporal cannot be null");
        Objects.requireNonNull(format, "format cannot be null");
        Objects.requireNonNull(onFallback, "onFallback cannot be null");

        try {
            return formatter.format(temporal, format);
        }
        catch (IllegalArgumentException ex) {
            return onFallback.get();
        }
    }

    @Override
    public String serialize(@NotNull Temporal temporal, @NotNull CompactTemporalFormat format) {
        Objects.requireNonNull(temporal, "temporal cannot be null");
        Objects.requireNonNull(format, "format cannot be null");

        try {
            return formatter.format(temporal, format);
        }
        catch (IllegalArgumentException ex) {
            throw new TemporalSerializationException(temporal, ex);
        }
    }

    static final class Holder {

        static final CompactTemporalSerializer INSTANCE = new CompactTemporalSerializerImpl();

    }
}
