package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

/**
 * A serializer for textual temporal values such as {@code 1 hour, 20 minutes, 30 seconds}.
 *
 * @see TextualTemporalFormat
 * @see TemporalSerializer
 * @since 2.0
 */
public interface TextualTemporalSerializer
        extends TemporalSerializer<Temporal, TextualTemporalFormat, String> {

    /**
     * Returns the singleton textual temporal serializer.
     *
     * @return the serializer
     * @since 2.0
     */
    @Contract(pure = true)
    static TextualTemporalSerializer textual() {
        return TextualTemporalSerializerImpl.Holder.INSTANCE;
    }

    @Override
    default Temporal deserialize(@NotNull String input) {
        return deserialize(input, TextualTemporalFormat.defaults());
    }

    @Override
    default Temporal deserializeOrFallback(@NotNull String input, @NotNull Supplier<Temporal> onFallback) {
        return deserializeOrFallback(input, TextualTemporalFormat.defaults(), onFallback);
    }

    @Override
    default String serialize(@NotNull Temporal temporal) {
        return serialize(temporal, TextualTemporalFormat.defaults());
    }

    @Override
    default String serializeOrFallback(@NotNull Temporal temporal, @NotNull Supplier<String> onFallback) {
        return serializeOrFallback(temporal, TextualTemporalFormat.defaults(), onFallback);
    }
}
