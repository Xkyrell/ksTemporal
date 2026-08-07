package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.LongUnaryOperator;

final class FastTemporal extends AbstractTemporal {

    private long millis;

    FastTemporal(long amount, @NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        this.millis = unit.convert(amount, TemporalUnit.MILLIS);
    }

    FastTemporal(long millis) {
        this.millis = millis;
    }

    @Override
    public Temporal operation(@NotNull LongUnaryOperator onOperator) {
        Objects.requireNonNull(onOperator, "onOperator cannot be null");
        millis = onOperator.applyAsLong(millis);
        return this;
    }

    @Override
    protected long toMillisInternal() {
        return millis;
    }

    @Override
    public String toString() {
        return "FastTemporal{millis=" + millis + '}';
    }
}
