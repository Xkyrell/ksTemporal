package me.xkyrell.temporal.format.registry;

import java.util.Map;

public interface TemporalRegistry<K, V> {

    Map<K, V> loadStyles();

}
