package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GenresOverlapSimilarityCalculatorTest {

    private final GenresOverlapSimilarityCalculator calculator = new GenresOverlapSimilarityCalculator();

    @Test
    void testCalculateSimilarityShouldReturnCorrectValue() {
        Book book1 = new Book("1", "Title1", "Author1", "Description1",
            List.of("Fantasy", "Adventure"), 4.5, 100, "URL1");
        Book book2 = new Book("2", "Title2", "Author2", "Description2",
            List.of("Fantasy", "Romance"), 4.2, 150, "URL2");

        double similarity = calculator.calculateSimilarity(book1, book2);
        assertEquals(0.5, similarity, 0.001, "Similarity should be correctly calculated.");
    }

    @Test
    void testCalculateSimilarityShouldReturnZeroWhenNoOverlap() {
        Book book1 = new Book("1", "Title1", "Author1", "Description1",
            List.of("Fantasy", "Adventure"), 4.5, 100, "URL1");
        Book book2 = new Book("2", "Title2", "Author2", "Description2",
            List.of("Romance", "Sci-Fi"), 4.2, 150, "URL2");

        double similarity = calculator.calculateSimilarity(book1, book2);
        assertEquals(0.0, similarity, 0.001, "Similarity should be zero when there is no overlap.");
    }

    @Test
    void testCalculateSimilarityShouldReturnZeroWhenGenresListIsEmpty() {
        Book book1 = new Book("1", "Title1", "Author1", "Description1",
            List.of("Fantasy", "Adventure"), 4.5, 100, "URL1");
        Book book2 = new Book("2", "Title2", "Author2", "Description2",
            List.of(), 4.2, 150, "URL2");

        double similarity = calculator.calculateSimilarity(book1, book2);
        assertEquals(0.0, similarity, 0.001, "Similarity should be zero when there is no overlap.");
    }

    @Test
    void testCalculateSimilarityShouldThrowExceptionForNullBooks() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculateSimilarity(null, null),
            "Should throw IllegalArgumentException for null books.");
    }
}
