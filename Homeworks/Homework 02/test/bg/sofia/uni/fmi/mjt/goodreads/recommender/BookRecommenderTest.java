package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookRecommenderTest {

    private BookRecommender bookRecommender;
    private SimilarityCalculator similarityCalculator;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        similarityCalculator = mock(SimilarityCalculator.class);

        book1 = new Book("1", "Book One", "Author One", "Description One",
            List.of("Fiction"), 4.5, 100, "http://example.com/1");
        book2 = new Book("2", "Book Two", "Author Two", "Description Two",
            List.of("Non-fiction"), 4.0, 50, "http://example.com/2");
        book3 = new Book("3", "Book Three", "Author Three", "Description Three",
            List.of("Fiction", "Adventure"), 3.8, 75, "http://example.com/3");

        Set<Book> books = Set.of(book1, book2, book3);
        bookRecommender = new BookRecommender(books, similarityCalculator);
    }

    @Test
    void testRecommendBooksWithMaxNExceedingAvailableBooks() {
        when(similarityCalculator.calculateSimilarity(book1, book2)).thenReturn(0.7);
        when(similarityCalculator.calculateSimilarity(book1, book3)).thenReturn(0.5);

        SortedMap<Book, Double> recommendations = bookRecommender.recommendBooks(book1, 10);

        assertEquals(2, recommendations.size(), "Recommendations should include all available books when maxN exceeds the set size.");
    }

    @Test
    void testRecommendBooksWithMaxNLessThanAvailableBooks() {
        when(similarityCalculator.calculateSimilarity(book1, book2)).thenReturn(0.7);
        when(similarityCalculator.calculateSimilarity(book1, book3)).thenReturn(0.5);

        SortedMap<Book, Double> recommendations = bookRecommender.recommendBooks(book1, 1);

        assertEquals(1, recommendations.size(), "Recommendations should include all available books when maxN exceeds the set size.");
        assertEquals(book2, recommendations.firstKey());
    }

    @Test
    void testRecommendBooksWithNoSimilarBooks() {
        when(similarityCalculator.calculateSimilarity(book1, book2)).thenReturn(0.0);
        when(similarityCalculator.calculateSimilarity(book1, book3)).thenReturn(0.0);

        SortedMap<Book, Double> recommendations = bookRecommender.recommendBooks(book1, 1);

        assertTrue(recommendations.isEmpty());
    }

    @Test
    void testRecommendBooksWithNegativeMaxN() {
        assertThrows(IllegalArgumentException.class,
            () -> bookRecommender.recommendBooks(book1, -1),
            "Should throw IllegalArgumentException when maxN is negative.");
    }

    @Test
    void testRecommendBooksWithNullOrigin() {
        assertThrows(IllegalArgumentException.class,
            () -> bookRecommender.recommendBooks(null, 2),
            "Should throw IllegalArgumentException when origin book is null.");
    }

}
