package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LocationsRuleTest {

    private static final int LOCATION_THRESHOLD = 3;
    private static final double RULE_WEIGHT = 0.7;

    private LocationsRule locationsRule;

    @BeforeEach
    void setUp() {
        locationsRule = new LocationsRule(LOCATION_THRESHOLD, RULE_WEIGHT);
    }

    @Test
    void testApplicableReturnsTrueWhenThresholdMet() {
        Transaction transaction1 = mock();
        Transaction transaction2 = mock();
        Transaction transaction3 = mock();

        when(transaction1.location()).thenReturn("Location1");
        when(transaction2.location()).thenReturn("Location2");
        when(transaction3.location()).thenReturn("Location3");

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertTrue(locationsRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseWhenThresholdNotMet() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.location()).thenReturn("Location1");
        when(transaction2.location()).thenReturn("Location1");

        List<Transaction> transactions = List.of(transaction1, transaction2);

        assertFalse(locationsRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsTrueWithMoreUniqueLocationsThanThreshold() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);
        Transaction transaction3 = mock(Transaction.class);
        Transaction transaction4 = mock(Transaction.class);

        when(transaction1.location()).thenReturn("Location1");
        when(transaction2.location()).thenReturn("Location2");
        when(transaction3.location()).thenReturn("Location3");
        when(transaction4.location()).thenReturn("Location4");

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        assertTrue(locationsRule.applicable(transactions));
    }

    @Test
    void testWeight() {
        assertEquals(RULE_WEIGHT, locationsRule.weight());
    }

    @Test
    void testApplicableReturnsFalseForEmptyTransactions() {
        List<Transaction> transactions = List.of();

        assertFalse(locationsRule.applicable(transactions));
    }
}
