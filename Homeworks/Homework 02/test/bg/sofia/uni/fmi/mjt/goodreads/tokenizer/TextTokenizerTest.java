package bg.sofia.uni.fmi.mjt.goodreads.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class TextTokenizerTest {

    private Reader stopwordsReaderMock;
    private TextTokenizer textTokenizer;

    @BeforeEach
    void setUp() {
        stopwordsReaderMock = new StringReader("the \n and \n is\n on\n at");
        textTokenizer = new TextTokenizer(stopwordsReaderMock);
    }

    @Test
    void testTokenizeRemoveStopwordsAndPunctuation() {
        String input = "The quick brown fox, jumps over the lazy dog.";

        List<String> tokens = textTokenizer.tokenize(input);

        assertEquals(List.of("quick", "brown", "fox", "jumps", "over", "lazy", "dog"), tokens,
            "Tokenization should remove stopwords and punctuation, returning valid tokens.");
    }

    @Test
    void testTokenizeReturnEmptyListForStopwordsOnlyInput() {
        String input = "the and is on at";

        List<String> tokens = textTokenizer.tokenize(input);

        assertTrue(tokens.isEmpty(), "Tokenization of only stopwords should result in an empty list.");
    }

    @Test
    void testStopwordsLoadStopwordsFromReader() {
        Set<String> stopwords = textTokenizer.stopwords();

        assertEquals(Set.of("the", "and", "is", "on", "at"), stopwords,
            "Stopwords should be correctly loaded from the provided Reader.");
    }

}
