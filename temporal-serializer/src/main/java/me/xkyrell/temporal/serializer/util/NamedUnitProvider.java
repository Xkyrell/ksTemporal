package me.xkyrell.temporal.serializer.util;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;

@ApiStatus.Internal
@FunctionalInterface
public interface NamedUnitProvider {

    static NamedUnitProvider plural(@NotNull String singular, @NotNull String plural) {
        Objects.requireNonNull(singular, "singular cannot be null");
        Objects.requireNonNull(plural, "plural cannot be null");
        return amount -> amount == 1 ? singular : plural;
    }

    static NamedUnitProvider fixed(@NotNull String name) {
        Objects.requireNonNull(name, "name cannot be null");
        return __ -> name;
    }

    String provide(long amount);

}
