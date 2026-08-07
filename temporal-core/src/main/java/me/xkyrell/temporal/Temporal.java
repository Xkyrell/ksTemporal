package me.xkyrell.temporal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.function.LongUnaryOperator;

@ApiStatus.NonExtendable
public interface Temporal extends TemporalValue {

    /**
     * Creates a new mutable temporal with minimal allocations.
     *
     * @param amount the time amount
     * @param unit the unit of the specified amount
     * @return a new mutable temporal
     * @since 2.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Temporal ofFast(long amount, @NotNull TemporalUnit unit) {
        return new FastTemporal(amount, unit);
    }

    /**
     * Creates a new immutable temporal.
     *
     * @param amount the time amount
     * @param unit the unit of the specified amount
     * @return a new immutable temporal
     * @since 1.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Temporal of(long amount, @NotNull TemporalUnit unit) {
        return new TemporalImpl(amount, unit);
    }

    /**
     * Creates a new mutable temporal from milliseconds with minimal allocations.
     *
     * @param millis the time amount in milliseconds
     * @return a new mutable temporal
     * @since 2.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Temporal ofFast(long millis) {
        return new FastTemporal(millis);
    }

    /**
     * Creates a new immutable temporal from milliseconds.
     *
     * @param millis the time amount in milliseconds
     * @return a new immutable temporal
     * @since 1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Temporal of(long millis) {
        return new TemporalImpl(millis);
    }

    /**
     * Creates a new immutable temporal representing the difference
     * between two milliseconds timestamps.
     *
     * @param from the start time in milliseconds
     * @param to the end time in milliseconds
     * @return a new immutable temporal
     * @since 1.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Temporal between(long from, long to) {
        return new TemporalImpl(to - from);
    }

    /**
     * Creates a new immutable temporal value representing the difference
     * between two temporal values.
     *
     * @param from the start temporal value
     * @param to the end temporal value
     * @return a new immutable temporal
     * @since 1.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    static Temporal between(@NotNull TemporalValue from, @NotNull TemporalValue to) {
        Objects.requireNonNull(from, "from cannot be null");
        Objects.requireNonNull(to, "to cannot be null");
        return between(to.get(TemporalUnit.MILLIS), from.get(TemporalUnit.MILLIS));
    }

    /**
     * Creates a new immutable copy of the specified temporal value.
     *
     * @param value the temporal value to copy
     * @return a new immutable temporal value
     * @since 1.0
     */
    @Contract(value = "_ -> new", pure = true)
    static Temporal from(@NotNull TemporalValue value) {
        Objects.requireNonNull(value, "value cannot be null");
        return new TemporalImpl(value.get(TemporalUnit.MILLIS));
    }

    /**
     * Returns a temporal representing zero milliseconds.
     *
     * @return the immutable zero temporal
     * @since 1.0
     */
    @Contract(pure = true)
    static Temporal zero() {
        return TemporalImpl.ZERO;
    }

    /**
     * Applies the specified operation to this value.
     *
     * @param onOperator the operation to apply
     * @return the result of applying the operation
     * @since 1.0
     */
    Temporal operation(@NotNull LongUnaryOperator onOperator);

    /**
     * Truncates this value to the specified unit, returning at least one unit.
     *
     * @param unit the unit
     * @return the truncated value
     * @since 2.0
     */
    Temporal truncateAtLeastOne(@NotNull TemporalUnit unit);

    /**
     * Truncates this value to the specified unit.
     *
     * @param unit the unit
     * @return the truncated value
     * @since 2.0
     */
    Temporal truncate(@NotNull TemporalUnit unit);

    /**
     * Returns a new value with the specified amount added.
     *
     * @param value the amount to add
     * @param unit the unit of the amount
     * @return the resulting value
     * @since 2.0
     */
    Temporal plus(long value, @NotNull TemporalUnit unit);

    /**
     * Returns a new value with the specified value added.
     *
     * @param value the value to add
     * @return the resulting value
     * @since 1.0
     */
    Temporal plus(@NotNull TemporalValue value);

    /**
     * Returns a new value with the specified amount subtracted.
     *
     * @param value the amount to subtract
     * @param unit the unit of the amount
     * @return the resulting value
     * @since 2.0
     */
    Temporal minus(long value, @NotNull TemporalUnit unit);

    /**
     * Returns a new value with the specified value subtracted.
     *
     * @param value the value to subtract
     * @return the resulting value
     * @since 1.0
     */
    Temporal minus(@NotNull TemporalValue value);

    /**
     * Returns a new value multiplied by the specified multiplicand.
     *
     * @param multiplicand the multiplicand
     * @return the resulting value
     * @since 1.0
     */
    Temporal multiply(long multiplicand);

    /**
     * Returns a new value divided by the specified divisor.
     *
     * @param divisor the divisor
     * @return the resulting value
     * @since 1.0
     */
    Temporal divide(long divisor);

    /**
     * Returns this value or the specified minimum value, whichever is greater.
     *
     * @param temporal the minimum value
     * @return this value if it is greater than or equal to the minimum, otherwise the minimum value
     * @since 2.0
     */
    default Temporal atLeast(@NotNull Temporal temporal) {
        return isLessThan(temporal) ? temporal : this;
    }

    /**
     * Returns this value or the specified maximum value, whichever is smaller.
     *
     * @param temporal the maximum value
     * @return this value if it is less than or equal to the maximum, otherwise the maximum value
     * @since 2.0
     */
    default Temporal atMost(@NotNull Temporal temporal) {
        return isGreaterThan(temporal) ? temporal : this;
    }

    /**
     * Returns the absolute value.
     *
     * @return the absolute value
     * @since 2.0
     */
    default Temporal abs() {
        return isNegative() ? multiply(-1L) : this;
    }
}
