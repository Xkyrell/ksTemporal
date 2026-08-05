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

}
