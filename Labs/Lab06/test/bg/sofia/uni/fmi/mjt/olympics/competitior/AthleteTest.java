package bg.sofia.uni.fmi.mjt.olympics.competitior;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Athlete;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AthleteTest {
    private Athlete athlete;

    @BeforeEach
    void setUp() {
        athlete = new Athlete("5", "John", "Italian");
    }

    @Test
    void testValidateMedalInvalidMedal() {
        assertThrows(IllegalArgumentException.class, () -> athlete.validateMedal(null),
            "Should throw IllegalArgumentException if given medal is null.");
    }

    @Test
    void testAddMedalInvalidMedal() {
        assertThrows(IllegalArgumentException.class, () -> athlete.addMedal(null),
            "Should throw IllegalArgumentException if given medal is null.");
    }

    @Test
    void testAddValidMedal() {
        athlete.addMedal(Medal.GOLD);

        assertTrue(athlete.getMedals().contains(Medal.GOLD),
            "The added medal should be present in the athlete's medal set.");
    }

    @Test
    void testAthleteEquality() {
        Athlete anotherAthlete = new Athlete("5", "John", "Italian");
        assertEquals(athlete, anotherAthlete,
            "Athletes with the same identifier, name, and nationality should be equal.");
    }

    @Test
    void testAthleteInequality() {
        Athlete differentAthlete = new Athlete("6", "Doe", "French");
        assertNotEquals(athlete, differentAthlete,
            "Athletes with different attributes should not be equal.");
    }

    @Test
    void testHashCodeConsistency() {
        Athlete anotherAthlete = new Athlete("5", "John", "Italian");
        assertEquals(athlete.hashCode(), anotherAthlete.hashCode(),
            "Athletes with the same attributes should have the same hash code.");
    }

}
