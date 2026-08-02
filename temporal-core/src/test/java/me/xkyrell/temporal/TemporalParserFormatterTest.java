package me.xkyrell.temporal;

import me.xkyrell.temporal.format.impl.*;
import me.xkyrell.temporal.format.registry.impl.*;
import me.xkyrell.temporal.format.style.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemporalParserFormatterTest {

    private static final TextualTemporalStyle TEXTUAL = TextualTemporalStyle.builder()
            .unit(new TextualTemporalRegistry())
            .pluralize(group -> (group == 1) ? 0 : 1)
            .build();

    private static final CompactTemporalStyle COMPACT = CompactTemporalStyle.builder()
            .unit(new CompactTemporalRegistry())
            .build();

    @Test
    void testCompactTemporalFormatter() throws Throwable {
        CompactTemporalFormatter formatter = new CompactTemporalFormatter("hh:mm:ss");
        String formatted = formatter.format(3_660_000L, COMPACT);

        assertEquals("01:01:00", formatted);
    }

    @Test
    void testCompactTemporalParser() throws NumberFormatException {
        CompactTemporalParser parser = new CompactTemporalParser("hh:mm:ss");
        long parsedMillis = parser.parse("01:01:00", COMPACT);

        assertEquals(3_660_000L, parsedMillis);
    }

    @Test
    void testTextualTemporalFormatter() throws Throwable {
        TextualTemporalFormatter formatter = new TextualTemporalFormatter();
        String formatted = formatter.format(3_600_000L + 60_000L, TEXTUAL);

        assertEquals("1 hour 1 minute", formatted);
    }

    @Test
    void testTextualTemporalParser() throws NumberFormatException {
        TextualTemporalParser parser = new TextualTemporalParser();
        long parsedMillis = parser.parse("1h 1m", TEXTUAL);

        assertEquals(3_600_000L + 60_000L, parsedMillis);
    }
}
