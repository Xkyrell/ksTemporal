package me.xkyrell.temporal;

import org.junit.jupiter.api.Test;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class TemporalTest {

    @Test
    void testZeroInstance() {
        assertEquals(0L, Temporal.ZERO.getMillis(), "ZERO instance should hve 0 millis");
    }

    @Test
    void testOfMillis() {
        Temporal temporal = Temporal.of(1000L);
        assertEquals(1000L, temporal.getMillis(), "Temporal#of should create an instance with specified millis");
    }

    @Test
    void testOfMillisWithUnit() {
        TemporalUnit seconds = TemporalUnit.SECONDS;
        Temporal temporal = Temporal.of(2L, seconds);
        assertEquals(2000L, temporal.getMillis(), "Temporal#of should calculate millis based on unit conversion");
    }

    @Test
    void testOfDuration() {
        Duration duration = Duration.ofSeconds(5);
        Temporal temporal = Temporal.of(duration);
        assertEquals(duration.toMillis(), temporal.getMillis(), "Temporal#of should create instance from Duration");
    }

    @Test
    void testBetweenMillis() {
        Temporal temporal = Temporal.between(1000L, 3000L);
        assertEquals(2000L, temporal.getMillis(), "Temporal#between should calculate the difference between millis");
    }

    @Test
    void testOperation() {
        Temporal temporal = Temporal.of(1000L);
        temporal.operation(millis -> millis + 500L);
        assertEquals(1500L, temporal.getMillis(), "Operation should apply function to millis");
    }

    @Test
    void testGetWithUnit() {
        Temporal temporal = Temporal.of(1000L);
        long result = temporal.get(TemporalUnit.SECONDS);
        assertEquals(1L, result, "Get should return millis converted by TemporalUnit");
    }

    @Test
    void testCompareTo() {
        Temporal greaterTemporal = Temporal.of(1000L);
        Temporal lesserTemporal = Temporal.of(500L);

        assertTrue(greaterTemporal.compareTo(lesserTemporal) > 0, "Temporal#compareTo should indicate greater value");
        assertTrue(lesserTemporal.compareTo(greaterTemporal) < 0, "Temporal#compareTo should indicate lesser value");
        assertEquals(0, greaterTemporal.compareTo(greaterTemporal), "Temporal#compareTo should return 0 for equal values");
    }

    @Test
    void testClone() {
        Temporal original = Temporal.of(1000L);
        Temporal cloned = original.clone();

        assertEquals(original, cloned, "Temporal#clone should create an identical instance");
        assertNotSame(original, cloned, "Temporal#clone should create a different instance");
    }

    @Test
    void testToDuration() {
        Temporal temporal = Temporal.of(1000L);
        assertEquals(Duration.ofMillis(1000L), temporal.toDuration(), "Temporal#toDuration should convert millis to Duration");
    }

    @Test
    void testIsValid() {
        assertTrue(Temporal.of(1000L).isValid(), "Temporal is valid if millis > 0");
        assertFalse(Temporal.of(0L).isValid(), "Temporal is invalid if millis <= 0");
    }

    @Test
    void testIsZero() {
        assertTrue(Temporal.of(0L).isZero(), "Temporal is zero if millis == 0");
        assertFalse(Temporal.of(1000L).isZero(), "Temporal is not zero if millis != 0");
    }
}
