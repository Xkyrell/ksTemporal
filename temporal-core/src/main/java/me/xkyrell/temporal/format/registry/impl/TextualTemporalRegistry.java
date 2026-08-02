package me.xkyrell.temporal.format.registry.impl;

import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.format.registry.TemporalEntry;
import me.xkyrell.temporal.format.registry.TemporalRegistry;
import java.util.HashMap;
import java.util.Map;

import static me.xkyrell.temporal.TemporalUnit.*;

public class TextualTemporalRegistry implements TemporalRegistry<String, TemporalEntry> {

    @Override
    public Map<String, TemporalEntry> loadStyles() {
        return new HashMap<String, TemporalEntry>() {{
            put("ff", entry(FOREVER, "forever"));
            put("y", entry(YEARS, "year", "years"));
            put("mo", entry(MONTHS, "month", "months"));
            put("d", entry(DAYS, "day", "days"));
            put("h", entry(HOURS, "hour", "hours"));
            put("m", entry(MINUTES, "minute", "minutes"));
            put("s", entry(SECONDS, "second", "seconds"));
            put("ms", entry(MILLIS, "millisecond", "milliseconds"));
        }};
    }

    private TemporalEntry entry(TemporalUnit unit, String... names) {
        return new TemporalEntry(unit, names);
    }
}
