package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.composite;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompositeSimilarityCalculatorTest {
    private final SimilarityCalculator genresCalculator = (b1, b2) -> 0.7;
    private final SimilarityCalculator tfidfCalculator = (b1, b2) -> 0.8;

    private final CompositeSimilarityCalculator compositeCalculator = new CompositeSimilarityCalculator(
        Map.of(genresCalculator, 0.5, tfidfCalculator, 0.5)
    );

    @Test
    void testCalculateSimilarityShouldReturnCorrectValue() {
        Book book1 = new Book("1", "Title1", "Author1", "Description1", List.of("Genre1"), 4.5, 100, "URL1");
        Book book2 = new Book("2", "Title2", "Author2", "Description2", List.of("Genre2"), 4.2, 150, "URL2");

        double similarity = compositeCalculator.calculateSimilarity(book1, book2);
        assertEquals(0.75, similarity, 0.001, "Composite similarity should be correctly calculated.");
    }

    @Test
    void testCalculateSimilarityShouldThrowExceptionForNullBooks() {
        assertThrows(IllegalArgumentException.class, () -> compositeCalculator.calculateSimilarity(null, null),
            "Should throw IllegalArgumentException for null books.");
    }
}
