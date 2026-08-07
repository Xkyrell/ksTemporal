package me.xkyrell.temporal.serializer;

import org.jetbrains.annotations.NotNull;

public interface TemporalFormat {

    /**
     * Returns the delimiter used to separate temporal units.
     *
     * @return the unit delimiter
     * @since 2.0
     */
    String getDelimiter();

    /**
     * Returns the maximum number of temporal units included during formatting.
     *
     * @return the number limit
     * @since 2.0
     */
    int getUnitLimit();

    interface SharedBuilder<B extends SharedBuilder<B, F>, F extends TemporalFormat> {

        /**
         * Sets the delimiter used to separate temporal units.
         *
         * @param delimiter the unit delimiter
         * @return this builder
         * @since 2.0
         */
        B delimiter(@NotNull String delimiter);

        /**
         * Sets the maximum number of temporal units to include during formatting.
         *
         * @param limit the maximum number of formatted units
         * @return this builder
         * @since 2.0
         */
        B unitLimit(int limit);

        /**
         * Builds a new temporal format.
         *
         * @return the created temporal format
         * @since 2.0
         */
        F build();

    }
}
