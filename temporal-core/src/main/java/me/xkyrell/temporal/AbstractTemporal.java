package me.xkyrell.temporal;

import org.jetbrains.annotations.NotNull;
import java.util.Objects;

abstract class AbstractTemporal implements Temporal {

    @Override
    public Temporal truncateAtLeastOne(@NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        long factor = unit.toMillis();
        return operation(millis -> {
            long truncated = (millis / factor) * factor;
            return truncated != 0L ? truncated : (millis < 0L ? -factor : factor);
        });
    }

    @Override
    public Temporal truncate(@NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        long factor = unit.toMillis();
        return operation(millis -> (millis / factor) * factor);
    }

    @Override
    public Temporal plus(long value, @NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        return operation(millis -> {
            return Math.addExact(millis, TemporalUnit.MILLIS.convert(value, unit));
        });
    }

    @Override
    public Temporal plus(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value cannot be null");
        return operation(millis -> {
            return Math.addExact(millis, value.get(TemporalUnit.MILLIS));
        });
    }

    @Override
    public Temporal minus(long value, @NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        return operation(millis -> {
            return Math.subtractExact(millis, TemporalUnit.MILLIS.convert(value, unit));
        });
    }

    @Override
    public Temporal minus(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value cannot be null");
        return operation(millis -> {
            return Math.subtractExact(millis, value.get(TemporalUnit.MILLIS));
        });
    }

    @Override
    public Temporal multiply(long multiplicand) {
        return operation(millis -> Math.multiplyExact(millis, multiplicand));
    }

    @Override
    public Temporal divide(long divisor) {
        if (divisor == 0L) {
            throw new ArithmeticException("Cannot divide a Temporal by zero");
        }
        return operation(millis -> millis / divisor);
    }

    @Override
    public long get(@NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        return unit.convert(toMillisInternal(), TemporalUnit.MILLIS);
    }

    @Override
    public boolean isMultipleOf(long amount, @NotNull TemporalUnit unit) {
        Objects.requireNonNull(unit, "unit cannot be null");
        long divisor = TemporalUnit.MILLIS.convert(amount, unit);
        if (divisor == 0L) {
            throw new ArithmeticException("Cannot determine multiplicity by zero");
        }

        return toMillisInternal() % divisor == 0L;
    }

    @Override
    public boolean isMultipleOf(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value cannot be null");
        return isMultipleOf(value.get(TemporalUnit.MILLIS), TemporalUnit.MILLIS);
    }

    @Override
    public int compareTo(@NotNull TemporalValue other) {
        Objects.requireNonNull(other, "other cannot be null");
        return Long.compare(toMillisInternal(), other.get(TemporalUnit.MILLIS));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTemporal that = (AbstractTemporal) o;
        return toMillisInternal() == that.toMillisInternal();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(toMillisInternal());
    }

    protected abstract long toMillisInternal();

}
