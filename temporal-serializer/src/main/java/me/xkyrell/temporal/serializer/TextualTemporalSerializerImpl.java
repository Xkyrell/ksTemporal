package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.Supplier;

final class TextualTemporalSerializerImpl implements TextualTemporalSerializer {

    private static final TemporalUnit[] UNITS = TemporalUnit.values();

    private final TextualTemporalParser parser;
    private final TextualTemporalFormatter formatter;

    private TextualTemporalSerializerImpl() {
        parser = new TextualTemporalParser(UNITS);
        formatter = new TextualTemporalFormatter(UNITS);
    }

    @Override
    public Temporal deserialize(@NotNull String input, @NotNull Iterable<? extends TextualTemporalFormat> formats) {
        Objects.requireNonNull(input, "input cannot be null");
        Objects.requireNonNull(formats, "formats cannot be null");

        TemporalSerializationException exception = null;
        for (TextualTemporalFormat format : formats) {
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
    public Temporal deserializeOrFallback(@NotNull String input, @NotNull TextualTemporalFormat format, @NotNull Supplier<Temporal> onFallback) {
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
    public Temporal deserialize(@NotNull String input, @NotNull TextualTemporalFormat format) {
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
    public String serializeOrFallback(@NotNull Temporal temporal, @NotNull TextualTemporalFormat format, @NotNull Supplier<String> onFallback) {
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
    public String serialize(@NotNull Temporal temporal, @NotNull TextualTemporalFormat format) {
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

        static final TextualTemporalSerializer INSTANCE = new TextualTemporalSerializerImpl();

    }
}
