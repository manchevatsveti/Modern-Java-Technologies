package bg.sofia.uni.fmi.mjt.goodreads.recommender;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class BookRecommender implements BookRecommenderAPI {

    Set<Book> initialBooks;
    SimilarityCalculator calculator;

    public BookRecommender(Set<Book> initialBooks, SimilarityCalculator calculator) {
        this.initialBooks = initialBooks;
        this.calculator = calculator;
    }

    @Override
    public SortedMap<Book, Double> recommendBooks(Book origin, int maxN) {
        if (origin == null) {
            throw new IllegalArgumentException("Origin book is null.");
        }

        if (maxN <= 0) {
            throw new IllegalArgumentException("Maximum number of entries must be greater than 0.");
        }

        SortedMap<Book, Double> recommendedBooks = calculateRecommendedBooks(origin, maxN);

        if (allValuesAreZero(recommendedBooks)) {
            return new TreeMap<>();
        }

        return recommendedBooks;
    }

    private SortedMap<Book, Double> calculateRecommendedBooks(Book origin, int maxN) {

        return initialBooks.stream()
            .filter(book -> !book.equals(origin))
            .collect(Collectors.toMap(
                book -> book,
                book -> calculator.calculateSimilarity(origin, book)
            ))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(maxN)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, _) -> e1,
                () -> new TreeMap<>(Comparator.comparingDouble((Book book)
                    -> calculator.calculateSimilarity(origin, book)).reversed())
            ));
    }

    private boolean allValuesAreZero(SortedMap<Book, Double> recommendedBooks) {
        return recommendedBooks.values()
            .stream()
            .allMatch(value -> Double.compare(value, 0.0) == 0);
    }

}
