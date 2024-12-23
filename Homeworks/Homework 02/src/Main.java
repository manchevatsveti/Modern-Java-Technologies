import bg.sofia.uni.fmi.mjt.goodreads.BookLoader;
import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.finder.BookFinder;
import bg.sofia.uni.fmi.mjt.goodreads.finder.MatchOption;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Reader rdBooks = new BufferedReader(new FileReader("goodreads_data.csv"));
        Reader rdWords = new BufferedReader(new FileReader("stopwords.txt"));

        Set<Book> books = BookLoader.load(rdBooks);
        TextTokenizer tokenizer = new TextTokenizer(rdWords);

        BookFinder bookFinder = new BookFinder(books, tokenizer);

        Set<String> keywords = Set.of("chronicles", "Narnia", "prince" );

        List<Book> match = bookFinder.searchByKeywords(keywords, MatchOption.MATCH_ALL);

    }
}
