package me.xkyrell.temporal.format;

import me.xkyrell.temporal.format.style.TemporalStyle;

public interface TemporalParser<S extends TemporalStyle> {

    long parse(String value, S style) throws NumberFormatException;

}
