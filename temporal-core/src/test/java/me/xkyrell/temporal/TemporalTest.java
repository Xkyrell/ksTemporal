package me.xkyrell.temporal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemporalTest {

    @Test
    void testZeroInstance() {
        assertEquals(0L, Temporal.zero().get(TemporalUnit.MILLIS));
    }

    @Test
    void testOfMillisWithUnit() {
        Temporal temporal = Temporal.of(2L, TemporalUnit.SECONDS)
                .plus(820L, TemporalUnit.MILLIS);

        assertEquals(2820L, temporal.get(TemporalUnit.MILLIS));
    }

    @Test
    void testBetweenMillis() {
        Temporal temporal = Temporal.between(1000L, 3000L);
        assertEquals(2000L, temporal.get(TemporalUnit.MILLIS));
    }

    @Test
    void testOperation() {
        Temporal temporal = Temporal.of(1000L);
        temporal = temporal.operation(millis -> millis + 500L);
        assertEquals(1500L, temporal.get(TemporalUnit.MILLIS));
    }

    @Test
    void testIsMultipleOf() {
        Temporal temporal = Temporal.of(2L, TemporalUnit.HOURS);
        assertTrue(temporal.isMultipleOf(120L, TemporalUnit.MINUTES));
        assertFalse(temporal.isMultipleOf(3L, TemporalUnit.HOURS));
        assertFalse(temporal.isMultipleOf(61L, TemporalUnit.MINUTES));
    }

    @Test
    void testTruncate() {
        Temporal temporal = Temporal.of(1L, TemporalUnit.DAYS)
                .plus(5L, TemporalUnit.HOURS)
                .plus(32L, TemporalUnit.MINUTES)
                .plus(45L, TemporalUnit.SECONDS);

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS),
                temporal.truncate(TemporalUnit.DAYS)
        );

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS)
                        .plus(5L, TemporalUnit.HOURS),
                temporal.truncate(TemporalUnit.HOURS)
        );

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS)
                        .plus(5L, TemporalUnit.HOURS)
                        .plus(32L, TemporalUnit.MINUTES),
                temporal.truncate(TemporalUnit.MINUTES)
        );

        assertEquals(temporal, temporal.truncate(TemporalUnit.SECONDS));
    }

    @Test
    void testTruncateAtLeastOne() {
        Temporal temporal = Temporal.of(1L, TemporalUnit.DAYS)
                .plus(5L, TemporalUnit.HOURS)
                .plus(32L, TemporalUnit.MINUTES)
                .plus(45L, TemporalUnit.SECONDS);

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS),
                temporal.truncateAtLeastOne(TemporalUnit.DAYS)
        );

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS)
                        .plus(5L, TemporalUnit.HOURS),
                temporal.truncateAtLeastOne(TemporalUnit.HOURS)
        );

        assertEquals(
                Temporal.of(1L, TemporalUnit.DAYS)
                        .plus(5L, TemporalUnit.HOURS)
                        .plus(32L, TemporalUnit.MINUTES),
                temporal.truncateAtLeastOne(TemporalUnit.MINUTES)
        );
    }

    @Test
    void testGetWithUnit() {
        Temporal temporal = Temporal.of(7000L);
        assertEquals(7L, temporal.get(TemporalUnit.SECONDS));
    }

    @Test
    void testCompareTo() {
        Temporal greaterTemporal = Temporal.of(1000L);
        Temporal lesserTemporal = Temporal.of(500L);

        assertTrue(greaterTemporal.compareTo(lesserTemporal) > 0);
        assertTrue(lesserTemporal.compareTo(greaterTemporal) < 0);
    }

    @Test
    void testClone() {
        Temporal original = Temporal.of(1000L);
        Temporal cloned = Temporal.from(original);

        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testTemporalValidation() {
        assertTrue(Temporal.of(1000L).isPositive());
        assertTrue(Temporal.of(-500L).isNegative());
        assertTrue(Temporal.of(0L).isZero());
    }
}
