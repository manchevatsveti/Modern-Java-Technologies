package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.genres;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;

import java.util.List;

public class GenresOverlapSimilarityCalculator implements SimilarityCalculator {

    @Override
    public double calculateSimilarity(Book first, Book second) {
        if ( first == null || second == null) {
            throw new IllegalArgumentException("First or second book is null.");
        }
        List<String> genresFirstBook = first.genres();
        List<String> genresSecondBook = second.genres();

        if (genresFirstBook == null || genresSecondBook == null ||
            genresFirstBook.isEmpty() || genresSecondBook.isEmpty()) {
            return 0.0;
        }

        long overlapSize = genresFirstBook.stream()
            .filter(genresSecondBook::contains)
            .count();

        int minSize = Math.min(genresFirstBook.size(), genresSecondBook.size());

        return overlapSize / (double) minSize;
    }

}
