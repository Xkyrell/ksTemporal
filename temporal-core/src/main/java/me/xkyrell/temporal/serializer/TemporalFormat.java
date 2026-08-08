package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalUnit;
import org.jetbrains.annotations.NotNull;

public interface TemporalFormat {

    /**
     * Returns the first temporal unit included during formatting.
     *
     * @return the first temporal unit
     * @since 2.0
     */
    TemporalUnit getFirstUnit();

    /**
     * Returns the last temporal unit included during formatting.
     *
     * @return the last temporal unit
     * @since 2.0
     */
    TemporalUnit getLastUnit();

    interface SharedBuilder<B extends SharedBuilder<B, F>, F extends TemporalFormat> {

        /**
         * Sets the first temporal unit to include during formatting.
         *
         * @param unit the first temporal unit
         * @return this builder
         * @since 2.0
         */
        B firstUnit(@NotNull TemporalUnit unit);

        /**
         * Sets the last temporal unit to include during formatting.
         *
         * @param unit the last temporal unit
         * @return this builder
         * @since 2.0
         */
        B lastUnit(@NotNull TemporalUnit unit);

        /**
         * Builds a new temporal format.
         *
         * @return the created temporal format
         * @since 2.0
         */
        F build();

    }
}
