# Unit Testing and Mocking

In addition to writing code from scratch, in practice, we often need to maintain, fix, or write tests for existing code.

The goal of today's exercise is to **create and execute JUnit tests** for the provided implementation.

---

## **MJT Olympics 🏃‍🏊‍🚴‍🏅**  
Welcome to the MJT Olympics: **Gold, Glory, and Code!**

The implementation can be found in the `resources` directory.

Unfortunately, there was no time to write tests for the system in the frantic preparation before the Games. As a result, there are some bugs hidden in the implementation. Your task is to find and fix them during testing. For efficient debugging:
1. **Write a test** for a specific scenario.
2. Fix the bug you identify through the test.

---

## **Core Classes and Their Functionality**

### **Competitor**
All Olympic Games competitors implement the `Competitor` interface. There is one primary implementation: `Athlete`.

The `Competitor` interface is as follows:

```java
package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.Collection;

public interface Competitor {

    /**
     * Returns the unique identifier of the competitor.
     */
    String getIdentifier();

    /**
     * Returns the name of the competitor.
     */
    String getName();

    /**
     * Returns the nationality of the competitor.
     */
    String getNationality();

    /**
     * Returns an unmodifiable collection of medals won by the competitor.
     */
    Collection<Medal> getMedals();

    /**
     * Adds a medal to the competitor's collection of medals.
     *
     * @throws IllegalArgumentException if the medal is null.
     */
    void addMedal(Medal medal);
}
```

---

### **Medal**
Athletes can win the following medals in competitions:

- 🏅 `GOLD`
- 🥈 `SILVER`
- 🥉 `BRONZE`

The `Medal` enumeration is defined as:

```java
package bg.sofia.uni.fmi.mjt.olympics.competitor;

public enum Medal {
    GOLD, SILVER, BRONZE;
}
```

---

### **Competition**
A competition in the Olympic Games is described using the following `record`:

```java
package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * @throws IllegalArgumentException when the competition name is null or blank
 * @throws IllegalArgumentException when the competition discipline is null or blank
 * @throws IllegalArgumentException when the competition's competitors is null or empty
 */
public record Competition(String name, String discipline, Set<Competitor> competitors) { // ... }
```

---

### **Olympics**
The core system logic resides in the `MJTOlympics` class, which implements the following interface:

```java
package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public interface Olympics {

    /**
     * Updates the competitors' medal statistics based on the competition result.
     *
     * @param competition the competition to update the statistics with
     * @throws IllegalArgumentException if the competition is null.
     * @throws IllegalArgumentException if a competitor is not registered in the Olympics.
     */
    void updateMedalStatistics(Competition competition);

    /**
     * Returns the nations, sorted in descending order based on the total medal count.
     * If two nations have an equal number of medals, they are sorted alphabetically.
     */
    TreeSet<String> getNationsRankList();

    /**
     * Returns the total number of medals won by competitors from the specified nationality.
     *
     * @param nationality the nationality of the competitors
     * @return the total number of medals
     * @throws IllegalArgumentException when nationality is null
     * @throws IllegalArgumentException when nationality is not registered in the Olympics.
     */
    int getTotalMedals(String nationality);

    /**
     * Returns a map of nations and their respective medal amount, won from each competition.
     *
     * @return the nations' medal table
     */
    Map<String, EnumMap<Medal, Integer>> getNationsMedalTable();

    /**
     * Returns the set of competitors registered for the Olympics.
     *
     * @return the set of registered competitors
     */
    Set<Competitor> getRegisteredCompetitors();
}
```

---

## **Package Structure**
Ensure the package names of the above-described classes match exactly, as otherwise, your solution cannot be tested by the grader.

```
src
└── bg.sofia.uni.fmi.mjt.olympics
     ├── comparator
     │     ├── NationMedalComparator.java
     │     └── (...)     
     ├── competition
     │     ├── Competition.java
     │     ├── CompetitionResultFetcher.java
     │     └── (...)     
     ├── competitor
     │     ├── Competitor.java
     │     ├── Medal.java
     │     ├── Athlete.java
     │     └── (...)
     ├── MJTOlympics.java
     ├── Olympics.java
     └── (...)     
test
└── bg.sofia.uni.fmi.mjt.olympics
     └── (...)
```
