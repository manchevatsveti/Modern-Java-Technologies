package bg.sofia.uni.fmi.mjt.olympics;

import bg.sofia.uni.fmi.mjt.olympics.competition.Competition;
import bg.sofia.uni.fmi.mjt.olympics.competition.CompetitionResultFetcher;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;
import bg.sofia.uni.fmi.mjt.olympics.competitor.Medal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.TreeSet;

@ExtendWith(MockitoExtension.class)
public class MJTOlympicsTest {

    private MJTOlympics olympics;
    private CompetitionResultFetcher resultFetcher;
    private final Competition competition = Mockito.mock();

    private Competitor competitor1;
    private Competitor competitor2;
    private Competitor competitor3;

    @BeforeEach
    void setUp() {
        resultFetcher = mock();

        competitor1 = mock();
        competitor2 = mock();
        competitor3 = mock();

        Set<Competitor> registeredCompetitors = new HashSet<>();
        registeredCompetitors.add(competitor1);
        registeredCompetitors.add(competitor2);
        registeredCompetitors.add(competitor3);

        olympics = new MJTOlympics(registeredCompetitors, resultFetcher);
    }

    @Test
    void testUpdateMedalStatisticsWithInvalidCompetition(){
        assertThrows(IllegalArgumentException.class, () -> olympics.updateMedalStatistics(null));
    }

    @Test
    void testUpdateMedalStatisticsWithUnregisteredCompetitors() {
        Set<Competitor> competitionCompetitors = Set.of(mock(Competitor.class)); // Unregistered competitor

        when(competition.competitors()).thenReturn(competitionCompetitors);

        assertThrows(IllegalArgumentException.class, () -> olympics.updateMedalStatistics(competition),
            "Expected to throw an exception when competition has unregistered competitors");
    }

    @Test
    void testUpdateMedalStatisticsMedalsOrder() {
        when(competitor1.getName()).thenReturn("Alice");
        when(competitor2.getName()).thenReturn("Bob");
        when(competitor3.getName()).thenReturn("Charlie");

        TreeSet<Competitor> ranking = new TreeSet<>(Comparator.comparing(Competitor::getName));
        ranking.add(competitor1);
        ranking.add(competitor2);
        ranking.add(competitor3);

        when(resultFetcher.getResult(competition)).thenReturn(ranking);

        olympics.updateMedalStatistics(competition);

        verify(competitor1).addMedal(Medal.GOLD);
        verify(competitor2).addMedal(Medal.SILVER);
        verify(competitor3).addMedal(Medal.BRONZE);
    }

    @Test
    void testGetNationsRankListCorrectCountryRanking() {
        when(competitor1.getName()).thenReturn("Alice");
        when(competitor1.getNationality()).thenReturn("CountryA");

        when(competitor2.getName()).thenReturn("Bob");
        when(competitor2.getNationality()).thenReturn("CountryB");

        when(competitor3.getName()).thenReturn("Charlie");
        when(competitor3.getNationality()).thenReturn("CountryC");

        TreeSet<Competitor> ranking = new TreeSet<>(Comparator.comparing(Competitor::getName));
        ranking.add(competitor1);
        ranking.add(competitor2);
        ranking.add(competitor3);

        when(resultFetcher.getResult(competition)).thenReturn(ranking);

        olympics.updateMedalStatistics(competition);

        TreeSet<String> nationsRankList = olympics.getNationsRankList();

        List<String> expectedOrder = List.of("CountryA", "CountryB", "CountryC");

        assertEquals(expectedOrder.size(), nationsRankList.size(), "The size of the nations rank list should match");
        assertIterableEquals(expectedOrder, nationsRankList, "The nations should be ranked correctly based on medals");
    }

    @Test
    void testGetTotalMedalsWithNullNationality() {
        assertThrows(IllegalArgumentException.class, () -> olympics.getTotalMedals(null),
            "Exception should be thrown when nationality is null.");
    }

    @Test
    void testGetTotalMedalsWithInvalidNationality() {
        when(competitor1.getName()).thenReturn("Alice");
        when(competitor1.getNationality()).thenReturn("CountryA");

        when(competitor2.getName()).thenReturn("Bob");
        when(competitor2.getNationality()).thenReturn("CountryB");

        when(competitor3.getName()).thenReturn("Charlie");
        when(competitor3.getNationality()).thenReturn("CountryC");

        TreeSet<Competitor> ranking = new TreeSet<>(Comparator.comparing(Competitor::getName));
        ranking.add(competitor1);
        ranking.add(competitor2);
        ranking.add(competitor3);
        when(resultFetcher.getResult(competition)).thenReturn(ranking);

        olympics.updateMedalStatistics(competition);

        assertThrows(IllegalArgumentException.class, () -> olympics.getTotalMedals("Germany"),
            "Exception should be thrown when the nationality is not in the medal table.");

    }
}
