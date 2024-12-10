package bg.sofia.uni.fmi.mjt.frauddetector.analyzer;

import bg.sofia.uni.fmi.mjt.frauddetector.rule.Rule;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Channel;
import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionAnalyzerImplTest {

    @Mock
    private Rule mockRule1;

    @Mock
    private Rule mockRule2;

    private TransactionAnalyzerImpl transactionAnalyzer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(mockRule1.weight()).thenReturn(0.6);
        when(mockRule2.weight()).thenReturn(0.4);
        when(mockRule1.applicable(anyList())).thenReturn(true);
        when(mockRule2.applicable(anyList())).thenReturn(false);

        String transactionData = """
            Leading line
            t1,acc1,100.50,2023-01-01 10:00:00,location1,ONLINE
            t2,acc2,200.75,2023-01-02 12:30:00,location2,BRANCH""";
        StringReader reader = new StringReader(transactionData);

        transactionAnalyzer = new TransactionAnalyzerImpl(reader, List.of(mockRule1, mockRule2));
    }

    @Test
    void testAllTransactions() {
        List<Transaction> transactions = transactionAnalyzer.allTransactions();

        assertNotNull(transactions);
        assertEquals(2, transactions.size());
        assertEquals("t1", transactions.get(0).transactionID());
        assertEquals("t2", transactions.get(1).transactionID());
    }

    @Test
    void testAllAccountIDs() {
        List<String> accountIds = transactionAnalyzer.allAccountIDs();

        assertNotNull(accountIds);
        assertEquals(2, accountIds.size());
        assertTrue(accountIds.contains("acc1"));
        assertTrue(accountIds.contains("acc2"));
    }

    @Test
    void testTransactionCountByChannel() {
        Map<Channel, Integer> transactionCount = transactionAnalyzer.transactionCountByChannel();

        assertNotNull(transactionCount);
        assertEquals(1, transactionCount.get(Channel.ONLINE));
        assertEquals(1, transactionCount.get(Channel.BRANCH));
    }

    @Test
    void testAmountSpentByUser() {
        double amount = transactionAnalyzer.amountSpentByUser("acc1");

        assertEquals(100.50, amount);

        amount = transactionAnalyzer.amountSpentByUser("acc2");

        assertEquals(200.75, amount);
    }

    @Test
    void testAllTransactionsByUser() {
        List<Transaction> transactions = transactionAnalyzer.allTransactionsByUser("acc1");

        assertNotNull(transactions);
        assertEquals(1, transactions.size());
        assertEquals("t1", transactions.getFirst().transactionID());
    }

    @Test
    void testAccountRating() {
        double rating = transactionAnalyzer.accountRating("acc1");

        assertEquals(0.6, rating);

        rating = transactionAnalyzer.accountRating("acc2");

        assertEquals(0.6, rating);
    }

    @Test
    void testAccountsRisk() {
        Map<String, Double> risks = transactionAnalyzer.accountsRisk();

        assertNotNull(risks);
        assertEquals(0.6, risks.get("acc1"));
        assertEquals(0.6, risks.get("acc2"));
    }

    @Test
    void testConstructorThrowsExceptionForNullReader() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new TransactionAnalyzerImpl(null, List.of(mockRule1))
        );

        assertEquals("Reader cannot be null.", exception.getMessage());
    }

    @Test
    void testConstructorThrowsExceptionForEmptyRules() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            new TransactionAnalyzerImpl(new StringReader(""), List.of())
        );

        assertEquals("Rules cannot be null or empty.", exception.getMessage());
    }

    @Test
    void testConstructorThrowsExceptionForInvalidRulesWeight() {
        when(mockRule1.weight()).thenReturn(0.7);
        when(mockRule2.weight()).thenReturn(0.4);

        assertThrows(IllegalArgumentException.class, () ->
            new TransactionAnalyzerImpl(new StringReader(""), List.of(mockRule1, mockRule2))
        );

    }

    @Test
    void testAccountRatingWithNull() {
        assertThrows(IllegalArgumentException.class, () -> transactionAnalyzer.accountRating(null));
    }

    @Test
    void testAllTransactionsByUserWithNull() {
        assertThrows(IllegalArgumentException.class, () -> transactionAnalyzer.allTransactionsByUser(null));
    }
}

