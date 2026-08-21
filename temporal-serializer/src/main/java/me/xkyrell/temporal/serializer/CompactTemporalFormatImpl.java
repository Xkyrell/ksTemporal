package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalUnit;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.Function;

final class CompactTemporalFormatImpl implements CompactTemporalFormat {

    static final CompactTemporalFormat DEFAULTS = createDefault(TemporalUnit.HOURS);
    static final CompactTemporalFormat SHORT_DEFAULTS = createDefault(TemporalUnit.MINUTES);

    private final Function<TemporalUnit, String> onDelimiter;
    private final boolean leadingZero;
    private final TemporalUnit firstUnit;
    private final TemporalUnit lastUnit;

    private CompactTemporalFormatImpl(
            Function<TemporalUnit, String> onDelimiter,
            boolean leadingZero,
            TemporalUnit firstUnit,
            TemporalUnit lastUnit
    ) {
        this.onDelimiter = onDelimiter;
        this.leadingZero = leadingZero;
        this.firstUnit = firstUnit;
        this.lastUnit = lastUnit;
    }

    private static CompactTemporalFormat createDefault(TemporalUnit firstUnit) {
        return new CompactTemporalFormatImpl(
                __ -> ":", true,
                firstUnit, TemporalUnit.SECONDS
        );
    }

    @Override
    public String getDelimiter(@NotNull TemporalUnit unit) {
        return onDelimiter.apply(unit);
    }

    @Override
    public boolean hasLeadingZero() {
        return leadingZero;
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

        private Function<TemporalUnit, String> onDelimiter;
        private boolean leadingZero;
        private TemporalUnit firstUnit;
        private TemporalUnit lastUnit;

        @Override
        public Builder delimiter(@NotNull Function<TemporalUnit, String> onDelimiter) {
            this.onDelimiter = onDelimiter;
            return this;
        }

        @Override
        public Builder leadingZero(boolean leadingZero) {
            this.leadingZero = leadingZero;
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
        public CompactTemporalFormat build() {
            Objects.requireNonNull(onDelimiter, "onDelimiter cannot be null");
            Objects.requireNonNull(firstUnit, "firstUnit cannot be null");
            Objects.requireNonNull(lastUnit, "lastUnit cannot be null");
            if (firstUnit.ordinal() < lastUnit.ordinal()) {
                throw new IllegalArgumentException("firstUnit cannot be smaller than lastUnit");
            }

            return new CompactTemporalFormatImpl(onDelimiter, leadingZero, firstUnit, lastUnit);
        }
    }
}
