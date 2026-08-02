package me.xkyrell.temporal;

import me.xkyrell.temporal.format.registry.impl.*;
import me.xkyrell.temporal.format.style.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemporalStyleTest {

    private static final TextualTemporalStyle.Builder TEXTUAL_BUILDER = TextualTemporalStyle.builder()
            .unit(new TextualTemporalRegistry());

    private static final CompactTemporalStyle.Builder COMPACT_BUILDER = CompactTemporalStyle.builder()
            .unit(new CompactTemporalRegistry());

    @Test
    void testTextualStylePluralize() {
        TEXTUAL_BUILDER.pluralize(millis -> (int) (millis % 2));
        TextualTemporalStyle style = TEXTUAL_BUILDER.build();

        assertEquals(1, style.applyPluralForm(3L));
        assertEquals(0, style.applyPluralForm(2L));
    }

    @Test
    void testConfigureTextualStyle() {
        TextualTemporalStyle style = TEXTUAL_BUILDER.build();
        assertFalse(style.getTemporalEntries().isEmpty());
    }

    @Test
    void testConfigureCompactStyle() {
        CompactTemporalStyle style = COMPACT_BUILDER.build();
        assertFalse(style.getUnits().isEmpty());
    }
}
