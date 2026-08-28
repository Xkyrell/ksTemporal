package me.xkyrell.temporal.serializer;

/**
 * Strategy for including zero values temporal units during formatting.
 *
 * @since 2.0
 */
public interface TextualZeroPolicy {

    /**
     * Always includes zero values temporal units in the result.
     *
     * @since 2.0
     */
    TextualZeroPolicy ALWAYS = new TextualZeroPolicy() {
        @Override
        public boolean shouldShowUnit(long value, int units, boolean hasRemainingAfter) {
            return true;
        }

        @Override
        public boolean shouldSmallestUnit() {
            return true;
        }
    };

    /**
     * Includes zero-valued temporal units when required by the remaining units.
     *
     * @since 2.0
     */
    TextualZeroPolicy REQUIRED = new TextualZeroPolicy() {
        @Override
        public boolean shouldShowUnit(long value, int units, boolean hasRemainingAfter) {
            return value != 0 || (units > 0 && hasRemainingAfter);
        }

        @Override
        public boolean shouldSmallestUnit() {
            return true;
        }
    };

    /**
     * Never includes zero values temporal units in the result.
     *
     * @since 2.0
     */
    TextualZeroPolicy NEVER = (value, __, ___) -> value != 0;

    /**
     * Returns whether the temporal unit should be included in the result.
     *
     * @param value the value of the temporal unit
     * @param units the number of units remaining after the current unit
     * @param hasRemainingAfter whether a non-zero value remains in smaller units
     * @return {@code true} if the temporal unit should be included
     * @since 2.0
     */
    boolean shouldShowUnit(long value, int units, boolean hasRemainingAfter);

    /**
     * Returns whether to include the smallest unit when zero.
     *
     * @return {@code true} if the smallest unit should be included
     * @since 2.0
     */
    default boolean shouldSmallestUnit() {
        return false;
    }
}
