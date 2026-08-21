package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.serializer.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static me.xkyrell.temporal.serializer.util.NamedUnitProvider.*;

final class TextualTemporalFormatImpl implements TextualTemporalFormat {

    static final TextualTemporalFormat SHORT_DEFAULTS = createDefault(
            " ", TextualTemporalFormatImpl::createShortUnitNames
    );
    static final TextualTemporalFormat SHORT_DEFAULTS_WITH_COMMAS = createDefault(
            ", ", TextualTemporalFormatImpl::createShortUnitNames
    );
    static final TextualTemporalFormat DEFAULTS = createDefault(
            " ", TextualTemporalFormatImpl::createFullUnitNames
    );
    static final TextualTemporalFormat DEFAULTS_WITH_COMMAS = createDefault(
            ", ", TextualTemporalFormatImpl::createFullUnitNames
    );

    private final IndexedFunction<TemporalUnit, String> onDelimiter;
    private final Map<TemporalUnit, NamedUnitProvider> providers;
    private final TextualZeroPolicy zeroPolicy;
    private final boolean unitSpacing;
    private final TemporalUnit firstUnit;
    private final TemporalUnit lastUnit;

    private TextualTemporalFormatImpl(
            IndexedFunction<TemporalUnit, String> onDelimiter,
            Map<TemporalUnit, NamedUnitProvider> providers,
            TextualZeroPolicy zeroPolicy, boolean unitSpacing,
            TemporalUnit firstUnit, TemporalUnit lastUnit
    ) {
        this.onDelimiter = onDelimiter;
        this.providers = providers;
        this.zeroPolicy = zeroPolicy;
        this.unitSpacing = unitSpacing;
        this.firstUnit = firstUnit;
        this.lastUnit = lastUnit;
    }

    private static TextualTemporalFormat createDefault(
            String delimiter, Supplier<Map<TemporalUnit, NamedUnitProvider>> onProviders
    ) {
        return new TextualTemporalFormatImpl(
                (__, ___, ____) -> delimiter,
                onProviders.get(), TextualZeroPolicy.NEVER, false,
                TemporalUnit.DAYS, TemporalUnit.SECONDS
        );
    }

    private static Map<TemporalUnit, NamedUnitProvider> createShortUnitNames() {
        Map<TemporalUnit, NamedUnitProvider> names = new EnumMap<>(TemporalUnit.class);
        names.put(TemporalUnit.DAYS, fixed("d"));
        names.put(TemporalUnit.HOURS, fixed("h"));
        names.put(TemporalUnit.MINUTES, fixed("m"));
        names.put(TemporalUnit.SECONDS, fixed("s"));
        return names;
    }

    private static Map<TemporalUnit, NamedUnitProvider> createFullUnitNames() {
        Map<TemporalUnit, NamedUnitProvider> names = new EnumMap<>(TemporalUnit.class);
        names.put(TemporalUnit.DAYS, plural("day", "days"));
        names.put(TemporalUnit.HOURS, plural("hour", "hours"));
        names.put(TemporalUnit.MINUTES, plural("minute", "minutes"));
        names.put(TemporalUnit.SECONDS, plural("second", "seconds"));
        return names;
    }

    @Override
    public String getDelimiter(@NotNull TemporalUnit unit, int current, int size) {
        return onDelimiter.apply(unit, current, size);
    }

    @Override
    public Map<TemporalUnit, NamedUnitProvider> getNamedUnitProviders() {
        return Collections.unmodifiableMap(providers);
    }

    @Override
    public TextualZeroPolicy getZeroPolicy() {
        return zeroPolicy;
    }

    @Override
    public boolean hasUnitSpacing() {
        return unitSpacing;
    }

    @Override
    public TemporalUnit getFirstUnit() {
        return firstUnit;
    }

    @Override
    public TemporalUnit getLastUnit() {
        return lastUnit;
    }

    static final class BuilderImpl implements Builder {

        private final Map<TemporalUnit, NamedUnitProvider> providers = new EnumMap<>(TemporalUnit.class);
        private IndexedFunction<TemporalUnit, String> onDelimiter;
        private TextualZeroPolicy zeroPolicy;
        private boolean unitSpacing;
        private TemporalUnit firstUnit;
        private TemporalUnit lastUnit;

        @Override
        public Builder delimiter(@NotNull IndexedFunction<TemporalUnit, String> onDelimiter) {
            this.onDelimiter = onDelimiter;
            return this;
        }

        @Override
        public Builder namedUnit(@NotNull TemporalUnit unit, @NotNull NamedUnitProvider provider) {
            providers.put(unit, provider);
            return this;
        }

        @Override
        public Builder namedUnits(@NotNull Map<TemporalUnit, NamedUnitProvider> providers) {
            this.providers.putAll(providers);
            return this;
        }

        @Override
        public Builder zeroPolicy(@Nullable TextualZeroPolicy zeroPolicy) {
            this.zeroPolicy = zeroPolicy;
            return this;
        }

        @Override
        public Builder unitSpacing(boolean unitSpacing) {
            this.unitSpacing = unitSpacing;
            return this;
        }

        @Override
        public Builder firstUnit(@NotNull TemporalUnit firstUnit) {
            this.firstUnit = firstUnit;
            return this;
        }

        @Override
        public Builder lastUnit(@NotNull TemporalUnit lastUnit) {
            this.lastUnit = lastUnit;
            return this;
        }

        @Override
        public TextualTemporalFormat build() {
            Objects.requireNonNull(onDelimiter, "onDelimiter cannot be null");
            Objects.requireNonNull(firstUnit, "firstUnit cannot be null");
            Objects.requireNonNull(lastUnit, "lastUnit cannot be null");
            if (firstUnit.ordinal() < lastUnit.ordinal()) {
                throw new IllegalArgumentException("firstUnit cannot be smaller than lastUnit");
            }

            if (providers.isEmpty()) {
                throw new IllegalArgumentException("providers cannot be empty");
            }

            return new TextualTemporalFormatImpl(onDelimiter, providers,
                    zeroPolicy == null ? TextualZeroPolicy.NEVER : zeroPolicy,
                    unitSpacing, firstUnit, lastUnit
            );
        }
    }
}
