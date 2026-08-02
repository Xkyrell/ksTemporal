package me.xkyrell.temporal.format.style;

import lombok.NonNull;
import me.xkyrell.temporal.format.TemporalFormatter;
import me.xkyrell.temporal.format.TemporalParser;
import me.xkyrell.temporal.util.Buildable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface TemporalStyle {

    static <B extends StyleBuilder<B, S>, S extends TemporalStyle>
    S configure(@NonNull B builder, @NonNull Consumer<? super B> building) {
        building.accept(builder);
        return builder.build();
    }

    String format(long millis);

    long parse(String value);

    interface StyleBuilder<B extends StyleBuilder<B, S>, S extends TemporalStyle> extends Buildable<S> {

        B formatter(@NonNull TemporalFormatter<S> formatter);

        B parser(@NonNull TemporalParser<S> parser);

        default B formatter(Supplier<? extends TemporalFormatter<S>> formatter) {
            return formatter(formatter.get());
        }

        default B parser(Supplier<? extends TemporalParser<S>> parser) {
            return parser(parser.get());
        }
    }
}
