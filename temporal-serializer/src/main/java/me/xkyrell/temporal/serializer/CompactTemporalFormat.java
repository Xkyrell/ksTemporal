package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalUnit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

/**
 * Represents the format used by {@link CompactTemporalSerializer}.
 *
 * @see TemporalFormat
 * @since 2.0
 */
public interface CompactTemporalFormat extends TemporalFormat {

    /**
     * Returns the default compact temporal format.
     *
     * <p>Formats values as {@code HH:MM:SS}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static CompactTemporalFormat defaults() {
        return CompactTemporalFormatImpl.DEFAULTS;
    }

    /**
     * Returns the default short compact temporal format.
     *
     * <p>Formats values as {@code MM:SS}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static CompactTemporalFormat shortDefaults() {
        return CompactTemporalFormatImpl.SHORT_DEFAULTS;
    }

    /**
     * Creates a builder for configuring a compact temporal format.
     *
     * @return a new format builder
     * @since 2.0
     */
    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new CompactTemporalFormatImpl.BuilderImpl();
    }

    /**
     * Returns the delimiter used after the specified temporal unit.
     *
     * @param unit the temporal unit
     * @return the delimiter
     * @since 2.0
     */
    String getDelimiter(@NotNull TemporalUnit unit);

    /**
     * Returns whether leading zeros are used for formatted values.
     *
     * @return {@code true} if leading zeros are enabled
     * @since 2.0
     */
    boolean hasLeadingZero();

    /**
     * A builder for configuring {@link CompactTemporalFormat}.
     *
     * @see SharedBuilder
     * @since 2.0
     */
    interface Builder extends SharedBuilder<Builder, CompactTemporalFormat> {

        /**
         * Sets the delimiter used after each temporal unit.
         *
         * @param onDelimiter the function that provides a delimiter
         * @return this builder
         * @since 2.0
         */
        Builder delimiter(@NotNull Function<TemporalUnit, String> onDelimiter);

        /**
         * Enables or disables leading zeros for formatted values.
         *
         * @param enabled {@code true} to enable leading zeros
         * @return this builder
         * @since 2.0
         */
        Builder leadingZero(boolean enabled);

        /**
         * Sets the delimiter for all temporal units.
         *
         * @param delimiter the delimiter
         * @return this builder
         * @since 2.0
         */
        default Builder delimiter(@NotNull String delimiter) {
            return delimiter(__ -> delimiter);
        }
    }
}
