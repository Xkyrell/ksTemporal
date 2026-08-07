package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import org.jetbrains.annotations.NotNull;

public interface TemporalSerializer<T extends Temporal, F extends TemporalFormat, R> {

    /**
     * Deserializes a temporal from an input of type {@code R}.
     *
     * @param input the serialized input
     * @param formats the formats to attempt during deserialization
     * @return the temporal
     * @throws IllegalArgumentException if the input cannot be deserialized using any of the specified formats
     * @since 2.0
     */
    T deserialize(@NotNull R input, @NotNull Iterable<? extends F> formats);

    /**
     * Deserializes a temporal from an input of type {@code R}.
     *
     * @param input the serialized input
     * @param format the format to use for deserialization
     * @return the temporal
     * @throws IllegalArgumentException if the input cannot be deserialized using the specified format
     * @since 2.0
     */
    T deserialize(@NotNull R input, @NotNull F format);

    /**
     * Serializes a temporal into an output of type {@code R}.
     *
     * @param temporal the temporal to serialize
     * @param formats the formats to attempt during serialization
     * @return the output
     * @throws IllegalArgumentException if the temporal cannot be serialized using any of the specified formats
     * @since 2.0
     */
    R serialize(@NotNull T temporal, @NotNull Iterable<? extends F> formats);

    /**
     * Serializes a temporal into an output of type {@code R}.
     *
     * @param temporal the temporal to serialize
     * @param format the format to use for serialization
     * @return the output
     * @throws IllegalArgumentException if the temporal cannot be serialized using the specified format
     * @since 2.0
     */
    R serialize(@NotNull T temporal, @NotNull F format);

}
