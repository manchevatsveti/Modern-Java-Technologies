package bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.descriptions;

import bg.sofia.uni.fmi.mjt.goodreads.book.Book;
import bg.sofia.uni.fmi.mjt.goodreads.recommender.similaritycalculator.SimilarityCalculator;
import bg.sofia.uni.fmi.mjt.goodreads.tokenizer.TextTokenizer;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TFIDFSimilarityCalculator implements SimilarityCalculator {

    private final Set<Book> books;
    private final TextTokenizer tokenizer;

    public TFIDFSimilarityCalculator(Set<Book> books, TextTokenizer tokenizer) {
        this.books = books;
        this.tokenizer = tokenizer;
    }

    /*
     * Do not modify!
     */
    @Override
    public double calculateSimilarity(Book first, Book second) {
        if ( first == null || second == null) {
            throw new IllegalArgumentException("First or second book is null.");
        }

        Map<String, Double> tfIdfScoresFirst = computeTFIDF(first);
        Map<String, Double> tfIdfScoresSecond = computeTFIDF(second);

        return cosineSimilarity(tfIdfScoresFirst, tfIdfScoresSecond);
    }

    public Map<String, Double> computeTFIDF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null.");
        }

        Map<String, Double> tfScores = computeTF(book);
        Map<String, Double> idfScores = computeIDF(book);

        return tfScores.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue() * idfScores.getOrDefault(entry.getKey(), 0.0)
            ));
    }

    public Map<String, Double> computeTF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null.");
        }

        List<String> words = tokenizer.tokenize(book.description());
        int totalWordsCount = words.size();

        Map<String, Long> wordsByCount =
            words.stream()
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        return wordsByCount.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue() / (double) totalWordsCount
            ));
    }

    public Map<String, Double> computeIDF(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book is null.");
        }

        int totalBooksCount = books.size();

        List<String> allWordsInAllBooks = books.stream()
            .flatMap(tempBook -> getDistinctWords(tempBook, tokenizer))
            .toList();

        Map<String, Long> wordsFrequencies = allWordsInAllBooks.stream()
            .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        return wordsFrequencies.entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> Math.log(totalBooksCount / (double) entry.getValue())
            ));
    }

    private Stream<String> getDistinctWords(Book book, TextTokenizer tokenizer) {
        List<String> words = tokenizer.tokenize(book.description());

        return words.stream().distinct();
    }

    private double cosineSimilarity(Map<String, Double> first, Map<String, Double> second) {
        double magnitudeFirst = magnitude(first.values());
        double magnitudeSecond = magnitude(second.values());

        return dotProduct(first, second) / (magnitudeFirst * magnitudeSecond);
    }

    private double dotProduct(Map<String, Double> first, Map<String, Double> second) {
        Set<String> commonKeys = new HashSet<>(first.keySet());
        commonKeys.retainAll(second.keySet());

        return commonKeys.stream()
            .mapToDouble(word -> first.get(word) * second.get(word))
            .sum();
    }

    private double magnitude(Collection<Double> input) {
        double squaredMagnitude = input.stream()
            .map(v -> v * v)
            .reduce(0.0, Double::sum);

        return Math.sqrt(squaredMagnitude);
    }
}
