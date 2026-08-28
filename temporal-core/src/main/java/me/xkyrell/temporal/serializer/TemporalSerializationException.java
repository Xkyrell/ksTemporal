package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalValue;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@ApiStatus.NonExtendable
public class TemporalSerializationException extends RuntimeException {

    public TemporalSerializationException(String input) {
      super(String.format("Cannot deserialize '%s': no matching format", input));
    }

    public TemporalSerializationException(String input, Throwable cause) {
      super(String.format("Cannot deserialize '%s': no matching format", input), cause);
    }

    public TemporalSerializationException(TemporalValue value, Throwable cause) {
      super(String.format("Cannot serialize '%s': no matching format", value), cause);
    }
}
