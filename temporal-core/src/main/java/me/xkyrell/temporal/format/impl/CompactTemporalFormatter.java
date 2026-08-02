package me.xkyrell.temporal.format.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.xkyrell.temporal.TemporalUnit;
import me.xkyrell.temporal.format.TemporalFormatter;
import me.xkyrell.temporal.format.style.CompactTemporalStyle;

import java.util.Map;
import java.util.StringJoiner;

@RequiredArgsConstructor
public class CompactTemporalFormatter implements TemporalFormatter<CompactTemporalStyle> {

    @NonNull
    private final String pattern;

    @Override
    public String format(long millis, CompactTemporalStyle style) throws Throwable {
        Map<String, TemporalUnit> units = style.getUnits();
        if (units.isEmpty() || millis < 0) {
            return "";
        }

        StringJoiner formattedTime = new StringJoiner(":");
        for (String unitPattern : pattern.split(":")) {
            TemporalUnit unit = units.get(unitPattern);
            if (unit == null) {
                continue;
            }

            long unitValue = millis / unit.toMillis();
            millis %= unit.toMillis();

            formattedTime.add(String.format("%02d", unitValue));
        }
        return formattedTime.toString();
    }
}
