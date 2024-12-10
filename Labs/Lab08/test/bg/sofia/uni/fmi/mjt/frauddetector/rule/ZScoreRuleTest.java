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

class ZScoreRuleTest {

    private static final double Z_SCORE_THRESHOLD = 1.0;
    private static final double RULE_WEIGHT = 0.5;

    private ZScoreRule zScoreRule;

    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;

    @BeforeEach
    void setUp() {
        transaction1 = mock();
        transaction2 = mock();
        transaction3 = mock();
        zScoreRule = new ZScoreRule(Z_SCORE_THRESHOLD, RULE_WEIGHT);
    }

    @Test
    void testApplicableReturnsTrueWhenZScoreThresholdExceeded() {

        when(transaction1.transactionAmount()).thenReturn(1.0);
        when(transaction2.transactionAmount()).thenReturn(5.0);
        when(transaction3.transactionAmount()).thenReturn(100.0);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertTrue(zScoreRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseWhenZScoreThresholdNotExceeded() {

        when(transaction1.transactionAmount()).thenReturn(1.0);
        when(transaction2.transactionAmount()).thenReturn(1.0);
        when(transaction3.transactionAmount()).thenReturn(1.0);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertFalse(zScoreRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseForEmptyTransactions() {
        List<Transaction> transactions = List.of();

        assertFalse(zScoreRule.applicable(transactions));
    }

    @Test
    void testApplicableReturnsFalseWhenStandardDeviationIsZero() {

        when(transaction1.transactionAmount()).thenReturn(10.0);
        when(transaction2.transactionAmount()).thenReturn(10.0);
        when(transaction3.transactionAmount()).thenReturn(10.0);

        List<Transaction> transactions = List.of(transaction1, transaction2, transaction3);

        assertFalse(zScoreRule.applicable(transactions));
    }

    @Test
    void testWeight() {
        assertEquals(RULE_WEIGHT, zScoreRule.weight());
    }
}
