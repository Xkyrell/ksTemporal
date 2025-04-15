package me.xkyrell.temporal.format.impl;

import me.xkyrell.temporal.format.TemporalFormatter;
import me.xkyrell.temporal.format.registry.TemporalEntry;
import me.xkyrell.temporal.format.style.TextualTemporalStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class TextualTemporalFormatter implements TemporalFormatter<TextualTemporalStyle> {

    @Override
    public String format(long millis, TextualTemporalStyle style) throws Throwable {
        if (style.getTemporalEntries().isEmpty() || millis < 0) {
            return "";
        }

        StringJoiner joiner = new StringJoiner(" ");
        List<TemporalEntry> sortedEntries = new ArrayList<>(style.getTemporalEntries().values());
        sortedEntries.sort((a, b) -> Long.compare(b.getMillis(), a.getMillis()));

        for (TemporalEntry entry : sortedEntries) {
            long amount = millis / entry.getMillis();
            if (amount > 0L) {
                int index = style.applyPluralForm(amount);
                String unitName = getUnitOrDefault(entry.getUnitNames(), index);
                joiner.add(Long.toString(amount)).add(unitName);
                millis %= entry.getMillis();
            }
        }

        if (joiner.length() == 0 && style.includesSmallestUnit()) {
            int index = style.applyPluralForm(1);
            String unitName = getUnitOrDefault(sortedEntries.getLast().getUnitNames(), index);
            joiner.add(Long.toString(0)).add(unitName);
        }

        return joiner.toString();
    }

    private String getUnitOrDefault(String[] unitNames, int index) {
        return (unitNames.length != 0 && index >= 0)
                ? (index < unitNames.length ? unitNames[index] : unitNames[0])
                : "?";
    }
}
