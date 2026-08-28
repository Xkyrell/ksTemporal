package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.serializer.util.IndexedFunction;
import me.xkyrell.temporal.serializer.util.NamedUnitProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import java.util.Map;

/**
 * Represents the format used by {@link TextualTemporalSerializer}.
 *
 * @see TemporalFormat
 * @since 2.0
 */
public interface TextualTemporalFormat extends TemporalFormat {

    /**
     * Returns the default short textual temporal format.
     *
     * <p>Formats values as {@code 1h 20m 30s}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static TextualTemporalFormat shortDefaults() {
        return TextualTemporalFormatImpl.SHORT_DEFAULTS;
    }

    /**
     * Returns the default short textual temporal format with comma and space delimiters.
     *
     * <p>Formats values as {@code 1h, 20m, 30s}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static TextualTemporalFormat shortDefaultsWithCommas() {
        return TextualTemporalFormatImpl.SHORT_DEFAULTS_WITH_COMMAS;
    }

    /**
     * Returns the default textual temporal format.
     *
     * <p>Formats values as {@code 1 hour 20 minutes 30 seconds}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static TextualTemporalFormat defaults() {
        return TextualTemporalFormatImpl.DEFAULTS;
    }

    /**
     * Returns the default textual temporal format with comma and space delimiters.
     *
     * <p>Formats values as {@code 1 hour, 20 minutes, 30 seconds}.</p>
     *
     * @return the default format
     * @since 2.0
     */
    @Contract(pure = true)
    static TextualTemporalFormat defaultsWithCommas() {
        return TextualTemporalFormatImpl.DEFAULTS_WITH_COMMAS;
    }

    /**
     * Creates a builder for configuring a textual temporal format.
     *
     * @return a new format builder
     * @since 2.0
     */
    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new TextualTemporalFormatImpl.BuilderImpl();
    }

    /**
     * Returns the delimiter used after the specified temporal unit.
     *
     * @param unit the temporal unit
     * @param current the zero-based index of the current unit
     * @param size the total number of formatted units
     * @return the delimiter
     * @since 2.0
     */
    String getDelimiter(@NotNull TemporalUnit unit, int current, int size);

    /**
     * Returns the named unit providers used to format temporal units.
     *
     * @return the named unit providers
     * @since 2.0
     */
    @Unmodifiable
    Map<TemporalUnit, NamedUnitProvider> getNamedUnitProviders();

    /**
     * Returns the policy used to determine how zero-valued units are formatted.
     *
     * @return the policy
     * @since 2.0
     */
    TextualZeroPolicy getZeroPolicy();

    /**
     * Returns whether a space is inserted between a value and its unit name.
     *
     * @return {@code true} if unit spacing is enabled
     * @since 2.0
     */
    boolean hasUnitSpacing();

    /**
     * A builder for configuring {@link TextualTemporalFormat}.
     *
     * @see SharedBuilder
     * @since 2.0
     */
    interface Builder extends SharedBuilder<Builder, TextualTemporalFormat> {

        /**
         * Sets the delimiter used after each temporal unit.
         *
         * @param onDelimiter the function that provides a delimiter
         * @return this builder
         * @since 2.0
         */
        Builder delimiter(@NotNull IndexedFunction<TemporalUnit, String> onDelimiter);

        /**
         * Sets the named unit provider for the specified temporal unit.
         *
         * @param unit the temporal unit
         * @param provider the named unit provider
         * @return this builder
         * @since 2.0
         */
        Builder namedUnit(@NotNull TemporalUnit unit, @NotNull NamedUnitProvider provider);

        /**
         * Sets the named unit providers.
         *
         * @param providers the named unit providers
         * @return this builder
         * @since 2.0
         */
        Builder namedUnits(@NotNull Map<TemporalUnit, NamedUnitProvider> providers);

        /**
         * Sets the policy used to determine how zero-valued units are formatted.
         *
         * @param policy the policy
         * @return this builder
         * @since 2.0
         */
        Builder zeroPolicy(@Nullable TextualZeroPolicy policy);

        /**
         * Enables or disables spacing between values and their unit names.
         *
         * @param enabled {@code true} to enable unit spacing
         * @return this builder
         * @since 2.0
         */
        Builder unitSpacing(boolean enabled);

        /**
         * Sets the delimiter for all temporal units.
         *
         * @param delimiter the delimiter
         * @return this builder
         * @since 2.0
         */
        default Builder delimiter(@NotNull String delimiter) {
            return delimiter((__, ___, ____) -> delimiter);
        }

        /**
         * Enables spacing between values and their unit names.
         *
         * @return this builder
         * @since 2.0
         */
        default Builder unitSpacing() {
            return unitSpacing(true);
        }
    }
}
