package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

/**
 * A {@link Temporal} serializer.
 *
 * @param <T> the temporal type
 * @param <F> the temporal format type
 * @param <R> the serialized input/output type
 * @since 2.0
 */
public interface TemporalSerializer<T extends Temporal, F extends TemporalFormat, R> {

    /**
     * Deserializes a temporal from an input of type {@code R}.
     *
     * @param input the serialized input
     * @param formats the formats to attempt during deserialization
     * @return the temporal
     * @throws TemporalSerializationException if deserialization fails with any specified formats
     * @since 2.0
     */
    T deserialize(@NotNull R input, @NotNull Iterable<? extends F> formats);

    /**
     * Deserializes a temporal from an input of type {@code R}, otherwise a fallback value
     *
     * @param input the serialized input
     * @param format the format to use for deserialization
     * @param onFallback the fallback value
     * @return the temporal
     * @since 2.0
     */
    T deserializeOrFallback(@NotNull R input, @NotNull F format, @NotNull Supplier<T> onFallback);

    /**
     * Deserializes a temporal from an input of type {@code R}.
     *
     * @param input the serialized input
     * @param format the format to use for deserialization
     * @return the temporal
     * @throws TemporalSerializationException if deserialization fails with the specified format
     * @since 2.0
     */
    T deserialize(@NotNull R input, @NotNull F format);

    /**
     * Deserializes a temporal from an input of type {@code R}, otherwise a fallback value
     *
     * @param input the serialized input
     * @param onFallback the fallback value
     * @return the temporal
     * @since 2.0
     */
    T deserializeOrFallback(@NotNull R input, @NotNull Supplier<T> onFallback);

    /**
     * Deserializes a temporal from an input of type {@code R}.
     *
     * @param input the serialized input
     * @return the temporal
     * @throws TemporalSerializationException if deserialization fails with the default format
     * @since 2.0
     */
    T deserialize(@NotNull R input);

    /**
     * Serializes a temporal into an output of type {@code R}, otherwise a fallback value
     *
     * @param temporal the temporal to serialize
     * @param format the format to use for serialization
     * @param onFallback the fallback value
     * @return the output
     * @since 2.0
     */
    R serializeOrFallback(@NotNull T temporal, @NotNull F format, @NotNull Supplier<R> onFallback);

    /**
     * Serializes a temporal into an output of type {@code R}.
     *
     * @param temporal the temporal to serialize
     * @param format the format to use for serialization
     * @return the output
     * @throws TemporalSerializationException if serialization fails with the specified format
     * @since 2.0
     */
    R serialize(@NotNull T temporal, @NotNull F format);

    /**
     * Serializes a temporal into an output of type {@code R}, otherwise a fallback value
     *
     * @param temporal the temporal to serialize
     * @param onFallback the fallback value
     * @return the output
     * @since 2.0
     */
    R serializeOrFallback(@NotNull T temporal, @NotNull Supplier<R> onFallback);

    /**
     * Serializes a temporal into an output of type {@code R}.
     *
     * @param temporal the temporal to serialize
     * @return the output
     * @throws TemporalSerializationException if serialization fails with the default format
     * @since 2.0
     */
    R serialize(@NotNull T temporal);

}
