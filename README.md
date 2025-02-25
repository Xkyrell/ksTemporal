# ksTemporal

A lightweight library for time manipulation, featuring its own time object along with built-in parsers and formatters.

#### Features
* Flexible styles
* The library fits in at under 100 KB.
* Supports the creation of custom parsers, formatters, and registries.
* Works with Java 8 and higher.
* Easy to use with an intuitive interface.

## Usage example
```java
// Create a Temporal object representing 3 minutes and add 5000 milliseconds to it
Temporal temporal = Temporal.of(3L, TemporalUnit.MINUTES).plus(Temporal.of(5000L));

// Flexible time operations
temporal.operation(millis -> {
    long randomValue = (new Random()).nextLong() % (millis + 1);
    if (randomValue < 0) {
        randomValue = -randomValue;
    }
    return randomValue;
});

// Get milliseconds (185000 milliseconds)
temporal.getMillis();

// Format to a readable string "3 minutes 5 seconds"
temporal.format();

// Format with a compact style "03:05"
// You can set your own time style
temporal.formatAs(CompactTemporalStyle.compact());

// Extract specific time unit "3 minutes"
temporal.get(TemporalUnit.MINUTES)

// Clone and manipulate the time object (A temporal clone with 2 seconds subtracted)
Temporal clone = temporal.clone().minus(Temporal.of(2L, TemporalUnit.SECONDS));

// Parse a time string "2h 44m 30s"
Temporal parsed = Temporal.parse("2h 44m 30s");

// Parse with compact style (formatted as "02:49")
// You can set your own time style
Temporal compactParsed = Temporal.parse("02:49", CompactTemporalStyle.compact());

// Create a builder to easily modify configurations
TextualTemporalStyle.builder();
CompactTemporalStyle.builder();
```
