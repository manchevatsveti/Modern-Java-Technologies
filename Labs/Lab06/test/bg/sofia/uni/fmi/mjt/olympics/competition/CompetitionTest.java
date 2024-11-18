package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;

public class CompetitionTest {

    private Set<Competitor> competitors;

    @BeforeEach
    void setUp() {
        Competitor competitor1 = mock(Competitor.class);
        Competitor competitor2 = mock(Competitor.class);

        competitors = new HashSet<>();
        competitors.add(competitor1);
        competitors.add(competitor2);
    }

    @Test
    void testEqualsAndHashCodeWithSameCompetition() {
        Competition competition1 = new Competition("100m Sprint", "Athletics", competitors);
        Competition competition2 = new Competition("100m Sprint", "Athletics", competitors);

        assertEquals(competition1, competition2, "Competitions with same name and discipline should be equal");
    }

    @Test
    void testEqualsAndHashCodeWithDiffCompetition() {
        Competition competition1 = new Competition("100m Sprint", "Athletics", competitors);
        Competition competition2 = new Competition("200m Sprint", "Athletics", competitors);

        assertNotEquals(competition1, competition2, "Competitions with same name and discipline should be equal");
    }
}
