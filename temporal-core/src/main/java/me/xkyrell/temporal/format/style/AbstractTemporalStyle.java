package me.xkyrell.temporal.format.style;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.xkyrell.temporal.format.TemporalFormatter;
import me.xkyrell.temporal.format.TemporalParser;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
abstract class AbstractTemporalStyle<S extends TemporalStyle> implements TemporalStyle {

    private final TemporalFormatter<S> formatter;
    private final TemporalParser<S> parser;

    @SuppressWarnings("unchecked")
    protected S self = (S) this;

    @Override
    public String format(long millis) {
        try {
            return formatter.format(millis, self);
        }
        catch (Throwable throwable) {
            throw new IllegalStateException(throwable);
        }
    }

    @Override
    public long parse(String value) {
        try {
            return parser.parse(value, self);
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Getter(AccessLevel.PACKAGE)
    abstract static class AbstractBuilder<B extends StyleBuilder<B, S>, S extends TemporalStyle> implements StyleBuilder<B, S> {

        private TemporalFormatter<S> formatter;
        private TemporalParser<S> parser;

        @SuppressWarnings("unchecked")
        protected B self = (B) this;

        @Override
        public B formatter(@NonNull TemporalFormatter<S> formatter) {
            this.formatter = formatter;
            return self;
        }

        @Override
        public B parser(@NonNull TemporalParser<S> parser) {
            this.parser = parser;
            return self;
        }
    }
}
