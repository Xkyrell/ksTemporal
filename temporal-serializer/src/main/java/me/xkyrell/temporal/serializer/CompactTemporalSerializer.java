package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

/**
 * A serializer for compact temporal values such as {@code 01:20:30}.
 *
 * @see CompactTemporalFormat
 * @see TemporalSerializer
 * @since 2.0
 */
public interface CompactTemporalSerializer
        extends TemporalSerializer<Temporal, CompactTemporalFormat, String> {

    /**
     * Returns the singleton compact temporal serializer.
     *
     * @return the serializer
     * @since 2.0
     */
    @Contract(pure = true)
    static CompactTemporalSerializer compact() {
        return CompactTemporalSerializerImpl.Holder.INSTANCE;
    }

    @Override
    default Temporal deserialize(@NotNull String input) {
        return deserialize(input, CompactTemporalFormat.defaults());
    }

    @Override
    default Temporal deserializeOrFallback(@NotNull String input, @NotNull Supplier<Temporal> onFallback) {
        return deserializeOrFallback(input, CompactTemporalFormat.defaults(), onFallback);
    }

    @Override
    default String serialize(@NotNull Temporal temporal) {
        return serialize(temporal, CompactTemporalFormat.defaults());
    }

    @Override
    default String serializeOrFallback(@NotNull Temporal temporal, @NotNull Supplier<String> onFallback) {
        return serializeOrFallback(temporal, CompactTemporalFormat.defaults(), onFallback);
    }
}
