package me.xkyrell.temporal.format;

import me.xkyrell.temporal.format.style.TemporalStyle;

public interface TemporalFormatter<S extends TemporalStyle> {

    String format(long millis, S style) throws Throwable;

}
