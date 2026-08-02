package me.xkyrell.temporal.format.impl;

import me.xkyrell.temporal.format.TemporalParser;
import me.xkyrell.temporal.format.registry.TemporalEntry;
import me.xkyrell.temporal.format.style.TextualTemporalStyle;
import java.util.Map;

public class TextualTemporalParser implements TemporalParser<TextualTemporalStyle> {

    @Override
    public long parse(String value, TextualTemporalStyle style) throws NumberFormatException {
        Map<String, TemporalEntry> entries = style.getTemporalEntries();
        if (entries.isEmpty() || value == null || value.isEmpty()) {
            return 0L;
        }

        long totalMillis = 0L;
        for (String line : value.toLowerCase().split(" ")) {
            for (String key : entries.keySet()) {
                if (!line.endsWith(key)) {
                    continue;
                }

                String part = line.substring(0, line.length() - key.length());
                long multiplicand = Long.parseLong(part);
                TemporalEntry entry = entries.get(key);
                if (entry != null) {
                    totalMillis += multiplicand * entry.getMillis();
                }
            }
        }
        return totalMillis;
    }
}
