package me.xkyrell.temporal.format.style;

import lombok.Getter;
import lombok.NonNull;
import me.xkyrell.temporal.format.TemporalFormatter;
import me.xkyrell.temporal.format.TemporalParser;
import me.xkyrell.temporal.format.impl.TextualTemporalFormatter;
import me.xkyrell.temporal.format.impl.TextualTemporalParser;
import me.xkyrell.temporal.format.registry.TemporalEntry;
import me.xkyrell.temporal.format.registry.impl.TextualTemporalRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

final class SimpleTextualTemporalStyle extends AbstractTemporalStyle<TextualTemporalStyle> implements TextualTemporalStyle {

    static final TextualTemporalStyle TEXTUAL = new SimpleBuilder()
            .pluralize(group -> (group == 1) ? 0 : 1)
            .formatter(TextualTemporalFormatter::new)
            .parser(TextualTemporalParser::new)
            .unit(new TextualTemporalRegistry())
            .build();

    @Getter
    private final Map<String, TemporalEntry> temporalEntries;
    private final Function<Long, Integer> pluralizer;
    private final boolean includeSmallestUnit;

    private SimpleTextualTemporalStyle(
            TemporalFormatter<TextualTemporalStyle> formatter,
            TemporalParser<TextualTemporalStyle> parser,
            Map<String, TemporalEntry> temporalEntries,
            Function<Long, Integer> pluralizer,
            boolean includeSmallestUnit
    ) {
        super(formatter, parser);

        this.temporalEntries = temporalEntries;
        this.pluralizer = pluralizer;
        this.includeSmallestUnit = includeSmallestUnit;
    }

    @Override
    public boolean includesSmallestUnit() {
        return includeSmallestUnit;
    }

    @Override
    public int applyPluralForm(long millis) {
        return pluralizer.apply(millis);
    }

    static final class SimpleBuilder extends AbstractBuilder<Builder, TextualTemporalStyle> implements Builder {

        private final Map<String, TemporalEntry> temporalEntries = new HashMap<>();
        private Function<Long, Integer> pluralizer;
        private boolean includeSmallestUnit;

        @Override
        public Builder includeSmallestUnit() {
            includeSmallestUnit = true;
            return self;
        }

        @Override
        public Builder pluralize(@NonNull Function<Long, Integer> pluralizer) {
            this.pluralizer = pluralizer;
            return self;
        }

        @Override
        public Builder unit(@NonNull Map<String, TemporalEntry> entries) {
            entries.forEach(this::unit);
            return self;
        }

        @Override
        public Builder unit(@NonNull String syntax, @NonNull TemporalEntry entry) {
            temporalEntries.putIfAbsent(syntax, entry);
            return self;
        }

        @Override
        public TextualTemporalStyle build() {
            return new SimpleTextualTemporalStyle(
                    getFormatter(), getParser(), temporalEntries,
                    pluralizer, includeSmallestUnit
            );
        }
    }
}
