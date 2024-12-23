package bg.sofia.uni.fmi.mjt.goodreads.finder;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BookFinder implements BookFinderAPI {

    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public BookFinder(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    public Set<Book> allBooks() {
        return Collections.unmodifiableSet(books);
    }

    @Override
    public List<Book> searchByAuthor(String authorName) {
        if (authorName == null || authorName.isEmpty()) {
            throw new IllegalArgumentException("Author name is null or empty.");
        }

        return books.stream()
            .filter(book -> book.author().equalsIgnoreCase(authorName))
            .toList();
    }

    @Override
    public Set<String> allGenres() {
        return books.stream()
            .flatMap(book -> book.genres().stream())
            .collect(Collectors.toSet());
    }

    @Override
    public List<Book> searchByGenres(Set<String> genres, MatchOption option) {
        if (genres == null) {
            throw new IllegalArgumentException("Genres set is null.");
        }

        return books.stream()
            .filter(book -> matchesGenres(book.genres(), genres, option))
            .toList();
    }

    private boolean matchesGenres(List<String> bookGenres, Set<String> genres, MatchOption option) {
        return switch (option) {
            case MATCH_ALL -> bookGenres.containsAll(genres);
            case MATCH_ANY -> genres.stream().anyMatch(bookGenres::contains);
        };
    }

    @Override
    public List<Book> searchByKeywords(Set<String> keywords, MatchOption option) {
        Set<String> keywordsTokenized = keywords.stream()
            .map(String::toLowerCase)
            .collect(Collectors.toSet());

        return books.stream()
            .filter(book -> matchesKeywords(book, keywordsTokenized, option))
            .toList();
    }

    private boolean matchesKeywords(Book book, Set<String> keywords, MatchOption option) {
        List<String> bookTokens = tokenizer.tokenize(book.title() + " " + book.description());
        return switch (option) {
            case MATCH_ANY -> bookTokens.stream().anyMatch(keywords::contains);
            case MATCH_ALL -> new HashSet<>(bookTokens).containsAll(keywords);
        };
    }

}
