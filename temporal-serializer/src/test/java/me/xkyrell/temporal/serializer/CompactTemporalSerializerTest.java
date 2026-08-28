package me.xkyrell.temporal.serializer;

import me.xkyrell.temporal.Temporal;
import me.xkyrell.temporal.TemporalUnit;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompactTemporalSerializerTest {

    private static final CompactTemporalSerializer SERIALIZER = CompactTemporalSerializer.compact();

    @Test
    void testDeserialize() {
        Temporal temporal = Temporal.of(62, TemporalUnit.SECONDS);
        assertEquals(temporal, SERIALIZER.deserialize("00:01:02"));
        assertEquals(Temporal.zero(), SERIALIZER.deserialize("00:00:00"));
    }

    @Test
    void testDeserializeWithCustomFormat() {
        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS)
                .plus(2, TemporalUnit.MINUTES)
                .plus(3, TemporalUnit.SECONDS)
                .plus(400, TemporalUnit.MILLIS);

        CompactTemporalFormat format = CompactTemporalFormat.builder()
                .delimiter(unit -> unit == TemporalUnit.SECONDS ? "." : ":")
                .firstUnit(TemporalUnit.HOURS)
                .lastUnit(TemporalUnit.MILLIS)
                .build();

        assertEquals(temporal, SERIALIZER.deserialize("1:2:3.400", format));
    }

    @Test
    void testDeserializeWithAnyFormats() {
        List<CompactTemporalFormat> formats = Arrays.asList(
                CompactTemporalFormat.shortDefaults(),
                CompactTemporalFormat.defaults()
        );

        Temporal temporal = Temporal.of(3, TemporalUnit.HOURS)
                .plus(2, TemporalUnit.MINUTES)
                .plus(1, TemporalUnit.SECONDS);

        assertEquals(temporal, SERIALIZER.deserialize("03:02:01", formats));

        Temporal temporal2 = Temporal.of(3, TemporalUnit.SECONDS);
        assertEquals(temporal2, SERIALIZER.deserialize("00:03", formats));
    }

    @Test
    void testSerialize() {
        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS);
        assertEquals("01:00:00", SERIALIZER.serialize(temporal));
    }

    @Test
    void testSerializeWithCustomFormat() {
        Temporal temporal = Temporal.of(1, TemporalUnit.HOURS)
                .plus(2, TemporalUnit.MINUTES)
                .plus(3, TemporalUnit.SECONDS)
                .plus(400, TemporalUnit.MILLIS);

        CompactTemporalFormat format = CompactTemporalFormat.builder()
                .delimiter(unit -> unit == TemporalUnit.SECONDS ? "." : ":")
                .leadingZero(true)
                .firstUnit(TemporalUnit.HOURS)
                .lastUnit(TemporalUnit.MILLIS)
                .build();

        assertEquals("01:02:03.400", SERIALIZER.serialize(temporal, format));
    }
}
