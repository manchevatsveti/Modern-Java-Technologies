package bg.sofia.uni.fmi.mjt.goodreads.book;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BookTest {

    @Test
    public void testOfCreatesBookSuccessfully() {
        String[] tokens = {
            "1",
            "To Kill a Mockingbird",
            "Harper Lee",
            "A novel about racism and injustice",
            "['Classics', 'Fiction']",
            "4.27",
            "5691311",
            "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        };

        Book book = Book.of(tokens);

        assertEquals("1", book.ID());
        assertEquals("To Kill a Mockingbird", book.title());
        assertEquals("Harper Lee", book.author());
        assertEquals("A novel about racism and injustice", book.description());
        assertEquals(List.of("Classics", "Fiction"), book.genres());
        assertEquals(4.27, book.rating());
        assertEquals(5691311, book.ratingCount());
        assertEquals("https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird", book.URL());
    }

    @Test
    public void testOfThrowsExceptionForInvalidTokensLength() {
        String[] invalidTokens = {
            "1",
            "To Kill a Mockingbird",
            "Harper Lee",
            "A novel about racism and injustice",
            "['Classics', 'Fiction']",
            "4.27"
        };

        assertThrows(IllegalArgumentException.class, () -> Book.of(invalidTokens));
    }

    @Test
    public  void testOfWithEmptyGenres() {
        String[] tokens = {
            "1",
            "To Kill a Mockingbird",
            "Harper Lee",
            "A novel about racism and injustice",
            "[]",
            "4.27",
            "5691311",
            "https://www.goodreads.com/book/show/2657.To_Kill_a_Mockingbird"
        };

        Book book = Book.of(tokens);

        assertTrue(book.genres().isEmpty());
    }
}
