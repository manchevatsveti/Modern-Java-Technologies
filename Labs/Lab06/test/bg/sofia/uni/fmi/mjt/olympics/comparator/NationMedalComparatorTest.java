package bg.sofia.uni.fmi.mjt.olympics.comparator;

import bg.sofia.uni.fmi.mjt.olympics.MJTOlympics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NationMedalComparatorTest {

    private NationMedalComparator comparator;
    private MJTOlympics olympics;

    @BeforeEach
    void setUp() {
        olympics = Mockito.mock();
        comparator = new NationMedalComparator(olympics);
    }

    @Test
    void testCompareTwoDifferentIntegersFirstIsBigger() {
        String nation1 = "Bulgaria";
        String nation2 = "Italy";

        when(olympics.getTotalMedals(nation1)).thenReturn(11);
        when(olympics.getTotalMedals(nation2)).thenReturn(10);

        assertEquals(-1, comparator.compare(nation1,nation2), "Comparing two different numbers should return 1 when first is bigger.");
    }

    @Test
    void testCompareTwoDifferentIntegersSecondIsBigger() {
        String nation1 = "Bulgaria";
        String nation2 = "Italy";

        when(olympics.getTotalMedals(nation1)).thenReturn(4);
        when(olympics.getTotalMedals(nation2)).thenReturn(10);

        assertEquals(1, comparator.compare(nation1,nation2), "Comparing two different numbers should return -1 when second is bigger.");
    }

    @Test
    void testCompareTwoEqualNumbersDifferentNames() {
        String nation1 = "Bulgaria";
        String nation2 = "Hungary";

        when(olympics.getTotalMedals(nation1)).thenReturn(5);
        when(olympics.getTotalMedals(nation2)).thenReturn(5);

        int result = Integer.signum(comparator.compare(nation1, nation2));

        assertEquals(-1, result, "Comparing when medals are equal should be based on the names of the countries.");
    }
}
