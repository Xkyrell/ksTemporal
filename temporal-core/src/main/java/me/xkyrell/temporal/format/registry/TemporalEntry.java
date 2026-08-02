package me.xkyrell.temporal.format.registry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.xkyrell.temporal.TemporalUnit;

@Getter
@RequiredArgsConstructor
public final class TemporalEntry {

    private final TemporalUnit unit;
    private final String[] unitNames;

    public long getMillis() {
        return unit.toMillis();
    }
}
