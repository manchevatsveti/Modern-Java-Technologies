package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class CompetitionTest {

    private Set<Competitor> competitors;
    private Competitor competitor1;
    private Competitor competitor2;

    @BeforeEach
    void setUp() {
        competitor1 = mock(Competitor.class);
        competitor2 = mock(Competitor.class);

        competitors = new HashSet<>();
        competitors.add(competitor1);
        competitors.add(competitor2);
    }

    @Test
    void testEqualsWithSameCompetition() {
        Competition competition1 = new Competition("100m Sprint", "Athletics", competitors);
        Competition competition2 = new Competition("100m Sprint", "Athletics", competitors);

        assertEquals(competition1, competition2,
            "Competitions with same name and discipline should be equal");
    }

    @Test
    void testEqualsWithDiffCompetition() {
        Competition competition1 = new Competition("100m Sprint", "Athletics", competitors);
        Competition competition2 = new Competition("200m Sprint", "Athletics", competitors);

        assertNotEquals(competition1, competition2,
            "Competitions with same name and discipline should be equal");
    }

    @Test
    void testHashCodeEqual(){
        Competition competition1 = new Competition("100m Sprint", "Athletics", competitors);
        Competition competition2 = new Competition("100m Sprint", "Athletics", competitors);

        assertEquals(competition1.hashCode(), competition2.hashCode(),
            "Competitions with same name and discipline should be equal");
    }

    @Test
    void testCompetitorsIsUnmodifiable() {
        Competitor competitor3 = Mockito.mock();
        Set<Competitor> competitors = Set.of(competitor1, competitor2);

        Competition competition = new Competition("100m Sprint", "Athletics", competitors);

        assertThrows(UnsupportedOperationException.class, () -> competition.competitors().add(competitor3),
            "The competitors set should be unmodifiable");
    }

    @Test
    void testCompetitionNameIsNull() {
        Set<Competitor> competitors = Set.of(competitor1, competitor2);
        assertThrows(IllegalArgumentException.class, () -> new Competition(null, "discipline", competitors),
            "The competitors set should be unmodifiable");
    }

    @Test
    void testCompetitionCompetitorsIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Competition("name", "discipline", null),
            "The competitors set should be unmodifiable");
    }

    @Test
    void testCompetitionDisciplineIsNull() {
        Set<Competitor> competitors = Set.of(competitor1, competitor2);
        assertThrows(IllegalArgumentException.class, () -> new Competition("name", null, competitors),
            "The competitors set should be unmodifiable");
    }
}
