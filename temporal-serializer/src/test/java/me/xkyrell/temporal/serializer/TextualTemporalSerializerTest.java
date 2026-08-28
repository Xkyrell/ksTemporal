package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextualTemporalSerializerTest {

    private static final TextualTemporalSerializer SERIALIZER = TextualTemporalSerializer.textual();

    @Test
    void testDeserialize() {
        Temporal temporal = Temporal.of(62, TemporalUnit.SECONDS);
        assertEquals(temporal, SERIALIZER.deserialize("1minute 2seconds"));
        assertEquals(Temporal.zero(), SERIALIZER.deserialize("0seconds"));
    }

    @Test
    void testDeserializeWithCustomFormat() {
        Temporal temporal = Temporal.of(10, TemporalUnit.DAYS)
                .plus(2, TemporalUnit.HOURS)
                .plus(3, TemporalUnit.MINUTES);

        TextualTemporalFormat shortFormat = TextualTemporalFormat.shortDefaults();
        TextualTemporalFormat format = TextualTemporalFormat.builder()
                .delimiter(shortFormat::getDelimiter)
                .namedUnits(shortFormat.getNamedUnitProviders())
                .firstUnit(TemporalUnit.DAYS)
                .lastUnit(TemporalUnit.SECONDS)
                .build();

        assertEquals(temporal, SERIALIZER.deserialize("10d 2h 3m", format));
    }

    @Test
    void testDeserializeWithAnyFormats() {
        List<TextualTemporalFormat> formats = Arrays.asList(
                TextualTemporalFormat.shortDefaults(),
                TextualTemporalFormat.defaults()
        );

        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS)
                .plus(5, TemporalUnit.MINUTES)
                .plus(10, TemporalUnit.SECONDS);

        assertEquals(temporal, SERIALIZER.deserialize("1hour 5minutes 10seconds", formats));
        assertEquals(temporal, SERIALIZER.deserialize("1h 5m 10s", formats));
    }

    @Test
    void testSerialize() {
        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS);
        assertEquals("1hour", SERIALIZER.serialize(temporal));
    }

    @Test
    void testSerializeWithCustomFormat() {
        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS)
                .plus(2, TemporalUnit.MINUTES)
                .plus(3, TemporalUnit.SECONDS);

        TextualTemporalFormat defaultFormat = TextualTemporalFormat.defaults();
        TextualTemporalFormat format = TextualTemporalFormat.builder()
                .delimiter((__, currentUnit, totalUnits) ->
                        currentUnit < (totalUnits - 1) ? ", " : " and "
                )
                .namedUnits(defaultFormat.getNamedUnitProviders())
                .unitSpacing()
                .firstUnit(defaultFormat.getFirstUnit())
                .lastUnit(defaultFormat.getLastUnit())
                .build();

        assertEquals("1 hour, 2 minutes and 3 seconds", SERIALIZER.serialize(temporal, format));
    }
}
