package me.xkyrell.temporal.format.registry.impl;

import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.format.registry.TemporalRegistry;
import java.util.HashMap;
import java.util.Map;

import static me.xkyrell.temporal.TemporalUnit.*;

public class CompactTemporalRegistry implements TemporalRegistry<String, TemporalUnit> {

    @Override
    public Map<String, TemporalUnit> loadStyles() {
        return new HashMap<String, TemporalUnit>() {{
            put("ff", FOREVER);
            put("yy", YEARS);
            put("mo", MONTHS);
            put("dd", DAYS);
            put("hh", HOURS);
            put("mm", MINUTES);
            put("ss", SECONDS);
            put("ms", MILLIS);
        }};
    }
}
