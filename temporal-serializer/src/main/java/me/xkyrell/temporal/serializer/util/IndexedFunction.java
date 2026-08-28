package me.xkyrell.temporal.serializer.util;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@FunctionalInterface
public interface IndexedFunction<T, R> {

    R apply(T t, int currentIndex, int totalIndexes);

}
