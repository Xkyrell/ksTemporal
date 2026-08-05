package me.xkyrell.temporal;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.function.LongUnaryOperator;

@ApiStatus.NonExtendable
public interface Temporal extends TemporalValue {

    /**
     * Applies the specified operation to this value.
     *
     * @param onOperator the operation to apply
     * @return the result of applying the operation
     * @since 1.0
     */
    Temporal operation(@NotNull LongUnaryOperator onOperator);

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
