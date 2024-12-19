package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TFIDFSimilarityCalculatorTest {

    private TextTokenizer tokenizer;
    private TFIDFSimilarityCalculator calculator;

    private Book book1;
    private Book book2;
    private Set<Book> books;

    @BeforeEach
    void setUp() {
        tokenizer = mock(TextTokenizer.class);

        book1 = new Book("1", "Book One", "Author One", "word1 word2 word3 word1", List.of("Fiction"), 4.5, 100, "http://example.com/1");
        book2 = new Book("2", "Book Two", "Author Two", "word2 word3 word4", List.of("Non-fiction"), 4.0, 50, "http://example.com/2");

        books = Set.of(book1, book2);
        calculator = new TFIDFSimilarityCalculator(books, tokenizer);
    }

    @Test
    void testComputeTFForValidInput() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));

        Map<String, Double> tfScores = calculator.computeTF(book1);

        assertEquals(0.5, tfScores.get("word1"), 0.001, "TF for word1 should be correctly calculated.");
        assertEquals(0.25, tfScores.get("word2"), 0.001, "TF for word2 should be correctly calculated.");
        assertEquals(0.25, tfScores.get("word3"), 0.001, "TF for word3 should be correctly calculated.");
    }

    @Test
    void testComputeIDFShouldBeHigherForWord1() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));
        when(tokenizer.tokenize(book2.description())).thenReturn(List.of("word2", "word3", "word4"));

        Map<String, Double> idfScores = calculator.computeIDF(book1);

        assertTrue(idfScores.get("word1") > idfScores.get("word2"), "IDF for word1 should be higher as it appears in fewer documents.");
        assertTrue(idfScores.containsKey("word4"), "IDF for word4 should exist as it appears in book2.");
    }

    @Test
    void testComputeIDFForValidInput() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));
        when(tokenizer.tokenize(book2.description())).thenReturn(List.of("word2", "word3", "word4"));

        Map<String, Double> idfScores = calculator.computeIDF(book1);

        assertEquals(Math.log(2.0 / 1.0), idfScores.get("word1"), "IDF for word1 should be higher as it appears in fewer documents.");
    }

    @Test
    void testComputeTFIDFShouldBeHigherForWord1() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));
        when(tokenizer.tokenize(book2.description())).thenReturn(List.of("word2", "word3", "word4"));

        Map<String, Double> tfIdfScores = calculator.computeTFIDF(book1);

        assertTrue(tfIdfScores.get("word1") > tfIdfScores.get("word2"), "TF-IDF for word1 should be higher than word2 as it is more frequent in book1.");
    }

    @Test
    void testComputeTFIDF() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));
        when(tokenizer.tokenize(book2.description())).thenReturn(List.of("word2", "word3", "word4"));

        Map<String, Double> tfIdfScores = calculator.computeTFIDF(book1);

        assertEquals(0.3465, tfIdfScores.get("word1"), 0.001, "TF-IDF for word1 should be correctly calculated.");
    }

    @Test
    void testCosineSimilarity() {
        when(tokenizer.tokenize(book1.description())).thenReturn(List.of("word1", "word2", "word3", "word1"));
        when(tokenizer.tokenize(book2.description())).thenReturn(List.of("word2", "word3", "word4"));

        double similarity = calculator.calculateSimilarity(book1, book2);

        assertTrue(similarity >= 0 && similarity <= 1, "Cosine similarity should be a value between 0 and 1.");
    }

    @Test
    void testComputeTFIDFWithNullBook() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTFIDF(null), "computeTFIDF should throw IllegalArgumentException for null book.");
    }

    @Test
    void testComputeTFWithNullBook() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeTF(null), "computeTF should throw IllegalArgumentException for null book.");
    }

    @Test
    void testCalculateSimilarityShouldThrowExceptionForNullBooks() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, null), "Should throw IllegalArgumentException for null books.");
    }

    @Test
    void testComputeIdfShouldThrowExceptionForNullBooks() {
        assertThrows(IllegalArgumentException.class, () -> calculator.computeIDF( null), "Should throw IllegalArgumentException for null book.");
    }
}
