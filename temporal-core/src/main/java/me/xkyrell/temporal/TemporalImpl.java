package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.LongUnaryOperator;

final class TemporalImpl extends AbstractTemporal {

    static final Temporal ZERO = new TemporalImpl(0L);

    private final long millis;

    TemporalImpl(long amount, @NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        this.millis = TemporalUnit.MILLIS.convert(amount, unit);
    }

    TemporalImpl(long millis) {
        this.millis = millis;
    }

    @Override
    public Temporal operation(@NotNull LongUnaryOperator onOperator) {
        Objects.requireNonNull(onOperator, "onOperator cannot be null");
        return new TemporalImpl(onOperator.applyAsLong(millis));
    }

    @Override
    protected long toMillisInternal() {
        return millis;
    }

    @Override
    public String toString() {
        return "TemporalImpl{millis=" + millis + '}';
    }
}
