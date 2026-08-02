package me.xkyrell.temporal;

import me.xkyrell.temporal.format.registry.TemporalEntry;
import me.xkyrell.temporal.format.registry.impl.*;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TemporalRegistryTest {

    @Test
    void testCompactTemporalRegistry() {
        CompactTemporalRegistry registry = new CompactTemporalRegistry();
        Map<String, TemporalUnit> units = registry.loadStyles();
        assertEquals(8, units.size());
        assertEquals(TemporalUnit.FOREVER, units.get("ff"));
        assertEquals(TemporalUnit.YEARS, units.get("yy"));
        assertEquals(TemporalUnit.MONTHS, units.get("mo"));
        assertEquals(TemporalUnit.DAYS, units.get("dd"));
        assertEquals(TemporalUnit.HOURS, units.get("hh"));
        assertEquals(TemporalUnit.MINUTES, units.get("mm"));
        assertEquals(TemporalUnit.SECONDS, units.get("ss"));
        assertEquals(TemporalUnit.MILLIS, units.get("ms"));
    }

    @Test
    void testTextualTemporalRegistry() {
        TextualTemporalRegistry registry = new TextualTemporalRegistry();
        Map<String, TemporalEntry> entries = registry.loadStyles();
        assertEquals(8, entries.size());
        assertNotNull(entries.get("y"));
        assertNotNull(entries.get("mo"));
        assertNotNull(entries.get("d"));
    }

    @Test
    void testTemporalEntry() {
        TemporalEntry entry = new TemporalEntry(TemporalUnit.MINUTES, new String[] { "minute", "minutes" });
        assertEquals(TemporalUnit.MINUTES, entry.getUnit());
        assertEquals(60_000L, entry.getMillis());
        assertArrayEquals(new String[] { "minute", "minutes" }, entry.getUnitNames());
    }
}
