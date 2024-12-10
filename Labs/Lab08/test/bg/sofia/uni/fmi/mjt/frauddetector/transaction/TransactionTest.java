package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionTest {

    @Mock
    private Transaction transaction;

    @Test
    void ofWithNull() {
        assertThrows(IllegalArgumentException.class, () -> Transaction.of(null));
    }

    @Test
    void ofWithEmpty() {
        assertThrows(IllegalArgumentException.class, () -> Transaction.of(""));
    }

    @Test
    void ofStringWithLessColumns() {
        assertThrows(IllegalArgumentException.class, () -> Transaction.of("abc,ef"));
    }
}
