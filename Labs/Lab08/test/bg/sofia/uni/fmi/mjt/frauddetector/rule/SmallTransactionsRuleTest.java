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

class SmallTransactionsRuleTest {

    private static final int COUNT_THRESHOLD = 3;
    private static final double AMOUNT_THRESHOLD = 50.0;
    private static final double RULE_WEIGHT = 0.5;

    private SmallTransactionsRule smallTransactionsRule;

    @BeforeEach
    void setUp() {
        smallTransactionsRule = new SmallTransactionsRule(COUNT_THRESHOLD, AMOUNT_THRESHOLD, RULE_WEIGHT);
    }

    @Test
    void testApplicableReturnsTrueWhenThresholdMet() {
        Transaction transaction1 = mock();
        Transaction transaction2 = mock();
        Transaction transaction3 = mock();

        when(transaction1.transactionAmount()).thenReturn(30.0);
        when(transaction2.transactionAmount()).thenReturn(20.0);
        when(transaction3.transactionAmount()).thenReturn(40.0);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertTrue(smallTransactionsRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseWhenThresholdNotMet() {
        Transaction transaction1 = mock(Transaction.class);
        Transaction transaction2 = mock(Transaction.class);

        when(transaction1.transactionAmount()).thenReturn(30.0);
        when(transaction2.transactionAmount()).thenReturn(60.0); // Above the threshold

        List<Transaction> transactions = List.of(transaction1, transaction2);

        assertFalse(smallTransactionsRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsTrueWithMoreTransactionsThanThreshold() {
        Transaction transaction1 = mock();
        Transaction transaction2 = mock();
        Transaction transaction3 = mock();
        Transaction transaction4 = mock();

        when(transaction1.transactionAmount()).thenReturn(10.0);
        when(transaction2.transactionAmount()).thenReturn(20.0);
        when(transaction3.transactionAmount()).thenReturn(30.0);
        when(transaction4.transactionAmount()).thenReturn(40.0);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3, transaction4);

        assertTrue(smallTransactionsRule.applicable(transactions));
    }

    @Test
    void testWeight() {
        assertEquals(RULE_WEIGHT, smallTransactionsRule.weight());
    }

    @Test
    void testApplicableReturnsFalseForEmptyTransactions() {
        List<Transaction> transactions = List.of();

        assertFalse(smallTransactionsRule.applicable(transactions));
    }
}
