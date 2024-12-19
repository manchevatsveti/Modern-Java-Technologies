package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TextTokenizer {

    private final Set<String> stopwords;

    public TextTokenizer(Reader stopwordsReader) {
        try (var br = new BufferedReader(stopwordsReader)) {
            stopwords = br.lines()
                .map(String::toLowerCase)
                .map(word -> word.replaceAll("\\p{Punct}", ""))
                .map(word -> word.replaceAll("\\s+", ""))
                .collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalArgumentException("Could not load dataset", ex);
        }
    }

    public List<String> tokenize(String input) {

        String[] result = input.split("\\s+");

        return Arrays.stream(result)
            .map(word -> word.replaceAll("\\p{Punct}", ""))
            .map(word -> word.replaceAll("\\s+", ""))
            .filter(word -> !word.isBlank()) // if there are any blank words left after the replacement
            .map(String::toLowerCase)
            .filter(word -> !stopwords().contains(word))
            .toList();
    }

    public Set<String> stopwords() {
        return stopwords;
    }

}
