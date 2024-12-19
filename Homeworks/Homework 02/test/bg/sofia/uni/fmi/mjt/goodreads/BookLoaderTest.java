package bg.sofia.uni.fmi.mjt.goodreads;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BookLoaderTest {

    @Test
    void testLoadEmptyFile() {
        String csvData = "ID,Title,Author,Description,Genres,Rating,RatingCount,URL\n";
        Reader reader = new StringReader(csvData);

        Set<Book> books = BookLoader.load(reader);

        assertTrue(books.isEmpty(), "The dataset should be empty when no books are present.");
    }

    @Test
    void testLoadInvalidFormat() {
        String csvData = """
            ID,Title,Author,Description,Genres,Rating,RatingCount,URL
            1,Book One,Author One,Description One,"['Fiction']",4.5,http://example.com/1
            """;

        Reader reader = new StringReader(csvData);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> BookLoader.load(reader),
            "Should throw IllegalArgumentException when the CSV format is invalid.");
    }

    @Test
    void testLoadValidData() {
        String csvData = """
            ID,Title,Author,Description,Genres,Rating,RatingCount,URL
            1,Book One,Author One,Description One,"['Fiction']",4.5,100,http://example.com/1
            2,Book Two,Author Two,Description Two,"['Non-fiction']",4.0,50,http://example.com/2
            """;

        Reader reader = new StringReader(csvData);
        Set<Book> books = BookLoader.load(reader);

        assertEquals(2, books.size(), "The dataset should contain two books.");
        assertTrue(books.stream().anyMatch(book -> book.title().equals("Book One")), "Book One should be in the dataset.");
        assertTrue(books.stream().anyMatch(book -> book.title().equals("Book Two")), "Book Two should be in the dataset.");
    }
}
