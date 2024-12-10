package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FrequencyRuleTest {

    private static final TemporalAmount TIME_WINDOW = ChronoUnit.DAYS.getDuration().multipliedBy(1); // 1 day
    private static final int TRANSACTION_THRESHOLD = 3;
    private static final double RULE_WEIGHT = 0.5;

    private FrequencyRule frequencyRule;

    @BeforeEach
    void setUp() {
        frequencyRule = new FrequencyRule(TRANSACTION_THRESHOLD, TIME_WINDOW, RULE_WEIGHT);
    }

    @Test
    void testApplicableReturnsTrueWhenThresholdMet() {
        Transaction transaction1 = mock();
        Transaction transaction2 = mock();
        Transaction transaction3 = mock();

        LocalDateTime now = LocalDateTime.now();
        when(transaction1.transactionDate()).thenReturn(now.minusHours(10));
        when(transaction2.transactionDate()).thenReturn(now.minusHours(5));
        when(transaction3.transactionDate()).thenReturn(now.minusHours(1));

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertTrue(frequencyRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseWhenThresholdNotMet() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        LocalDateTime now = LocalDateTime.now();
        when(transaction1.transactionDate()).thenReturn(now.minusHours(10));
        when(transaction2.transactionDate()).thenReturn(now.minusHours(5));

        List<Transaction> transactions = List.of(transaction1, transaction2);

        assertFalse(frequencyRule.applicable(transactions));
    }

    @Test
    void testApplicableThrowsExceptionForNullTransactions() {
       assertThrows(IllegalArgumentException.class, () ->
            frequencyRule.applicable(null)
        );
    }

    @Test
    void testApplicableThrowsExceptionForEmptyTransactions() {
        assertThrows(IllegalArgumentException.class, () ->
            frequencyRule.applicable(List.of())
        );
    }

    @Test
    void testWeight() {
        assertEquals(RULE_WEIGHT, frequencyRule.weight());
    }
}
