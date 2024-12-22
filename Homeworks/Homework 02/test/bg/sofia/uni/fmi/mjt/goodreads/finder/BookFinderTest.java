package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookFinderTest {

    private BookFinder bookFinder;
    private TextTokenizer tokenizerMock;

    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    void setUp() {
        tokenizerMock = mock();

        book1 = new Book("1", "Effective Java", "Joshua Bloch", "Java best practices",
            List.of("Programming", "Software Engineering", "Java"), 4.8, 1500, "http://example.com/book1");
        book2 = new Book("2", "Clean Code", "Robert Martin", "Writing cleaner code",
            List.of("Programming", "Software Engineering", "Code"), 4.7, 2000, "http://example.com/book2");
        book3 = new Book("3", "Clean Code 2", "Robert Martin", "-",
            List.of("Programming", "Software Engineering", "Code"), 4.7, 2000, "http://example.com/book2");

        bookFinder = new BookFinder(Set.of(book1, book2, book3), tokenizerMock);
    }

    @Test
    void testAllBooksShouldReturnAllBooks() {
        Set<Book> result = bookFinder.allBooks();
        Set<Book> expected = Set.of(book2, book1, book3);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "allBooks() should return all books.");
    }

    @Test
    void testSearchByAuthorShouldReturnBooksByAuthor() {
        List<Book> result = bookFinder.searchByAuthor("Robert Martin");
        Set<Book> expected = Set.of(book2, book3);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "searchByAuthor() should return books by the specified author.");
    }

    @Test
    void testSearchByAuthorShouldReturnEmptyListForUnknownAuthor() {
        List<Book> result = bookFinder.searchByAuthor("Unknown Author");

        assertTrue(result.isEmpty(), "searchByAuthor() should return an empty list for unknown authors.");
    }

    @Test
    void testSearchByAuthorShouldThrowExceptionForNullAuthor() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByAuthor(null),
            "searchByAuthor() should throw an exception for null author.");
    }

    @Test
    void testAllGenresShouldReturnAllGenres() {
        Set<String> genres = bookFinder.allGenres();
        Set<String> expected = Set.of("Code", "Java", "Programming", "Software Engineering");

        assertTrue(genres.size() == expected.size() && genres.containsAll(expected) && expected.containsAll(genres),
            "allGenres() should return all unique genres from the books.");
    }

    @Test
    void testSearchByGenresShouldReturnBooksMatchingAllGenres() {
        List<Book> result = bookFinder.searchByGenres(Set.of("Programming", "Code"), MatchOption.MATCH_ALL);
        List<Book> expected = List.of(book2, book3);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "searchByGenres() should return books matching all of the specified genres.");
    }

    @Test
    void testSearchByGenresShouldReturnBooksMatchingAnyGenre() {
        List<Book> result = bookFinder.searchByGenres(Set.of("Programming", "Java"), MatchOption.MATCH_ANY);
        List<Book> expected = List.of(book2, book3, book1);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "searchByGenres() should return books matching any of the specified genres.");
    }

    @Test
    void testSearchByGenresShouldThrowExceptionForNullGenres() {
        assertThrows(IllegalArgumentException.class, () -> bookFinder.searchByGenres(null, MatchOption.MATCH_ALL),
            "searchByGenres() should throw an exception for null genres.");
    }

    @Test
    void testSearchByKeywordsShouldReturnBooksMatchingAllKeywords() {
        when(tokenizerMock.tokenize("Effective Java"))
            .thenReturn(List.of("effective", "java"));

        when(tokenizerMock.tokenize("Java best practices"))
            .thenReturn(List.of("java", "best", "practices"));

        when(tokenizerMock.tokenize("Clean Code"))
            .thenReturn(List.of("clean", "code"));

        when(tokenizerMock.tokenize("Writing cleaner code"))
            .thenReturn(List.of("code", "writing", "cleaner"));

        when(tokenizerMock.tokenize("Clean Code 2"))
            .thenReturn(List.of("clean", "code", "2"));

        when(tokenizerMock.tokenize("-"))
            .thenReturn(List.of("-"));

        List<Book> result = bookFinder.searchByKeywords(Set.of("clean", "code", "writing"), MatchOption.MATCH_ALL);
        List<Book> expected = List.of(book2);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "searchByKeywords() should return books matching all of the specified keywords.");
    }

    @Test
    void testSearchByKeywordsShouldReturnBooksMatchingAnyKeywords() {
        when(tokenizerMock.tokenize("Effective Java"))
            .thenReturn(List.of("effective", "java"));

        when(tokenizerMock.tokenize("Java best practices"))
            .thenReturn(List.of("java", "best", "practices"));

        when(tokenizerMock.tokenize("Clean Code"))
            .thenReturn(List.of("clean", "code"));

        when(tokenizerMock.tokenize("Writing cleaner code"))
            .thenReturn(List.of("code", "writing", "cleaner"));

        when(tokenizerMock.tokenize("Clean Code 2"))
            .thenReturn(List.of("clean", "code", "2"));

        when(tokenizerMock.tokenize("-"))
            .thenReturn(List.of("-"));

        List<Book> result = bookFinder.searchByKeywords(Set.of("clean", "code", "best"), MatchOption.MATCH_ANY);
        List<Book> expected = List.of(book1, book2, book3);

        assertTrue(result.size() == expected.size() && result.containsAll(expected) && expected.containsAll(result),
            "searchByKeywords() should return books matching all of the specified keywords.");
    }

    @Test
    void testSearchByKeywordsShouldReturnEmptyListForNoMatches() {
        when(tokenizerMock.tokenize("Effective Java Java best practices"))
            .thenReturn(List.of("effective", "java", "best", "practices"));

        when(tokenizerMock.tokenize("Clean Code Writing cleaner code"))
            .thenReturn(List.of("clean", "code", "writing", "cleaner"));

        when(tokenizerMock.tokenize("Clean Code 2 -"))
            .thenReturn(List.of("clean", "code", "2", "-"));

        List<Book> result = bookFinder.searchByKeywords(Set.of("python"), MatchOption.MATCH_ANY);

        assertTrue(result.isEmpty(), "searchByKeywords() should return an empty list for no matches.");
    }

    @Test
    void testSearchByAuthorWithAuthorWithoutBooks() {
        List<Book> books = bookFinder.searchByAuthor("Nonexistent");

        assertTrue(books.isEmpty(), "List should be empty when an author has no books.");
    }
}
