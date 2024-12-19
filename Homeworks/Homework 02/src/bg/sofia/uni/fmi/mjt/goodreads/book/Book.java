package bg.sofia.uni.fmi.mjt.goodreads.book;

import java.util.Arrays;
import java.util.List;

public record Book(
    String ID,
    String title,
    String author,
    String description,
    List<String> genres,
    double rating,
    int ratingCount,
    String URL
) {

    private static final int ID_COLUMN = 0;
    private static final int TITLE_COLUMN = 1;
    private static final int AUTHOR_COLUMN = 2;
    private static final int DESCRIPTION_COLUMN = 3;
    private static final int GENRES_COLUMN = 4;
    private static final int RATING_COLUMN = 5;
    private static final int RATING_COUNT_COLUMN = 6;
    private static final int URL_COLUMN = 7;

    private static final int COLUMNS_COUNT = 8;

    public static Book of(String[] tokens) {
        if (tokens.length != COLUMNS_COUNT) {
            throw new IllegalArgumentException("Invalid number of tokens - must have 8.");
        }

        String ratingCountString = tokens[RATING_COUNT_COLUMN].replace(",", "");
        int ratingCount = Integer.parseInt(ratingCountString);

        String id = tokens[ID_COLUMN];
        String title = tokens[TITLE_COLUMN];
        String author = tokens[AUTHOR_COLUMN];
        String description = tokens[DESCRIPTION_COLUMN];
        List<String> genres = getGenres(tokens[GENRES_COLUMN]);
        double rating = Double.parseDouble(tokens[RATING_COLUMN]);
        String url = tokens[URL_COLUMN];

        return new Book(id, title, author, description, genres, rating, ratingCount, url);
    }

    private static List<String> getGenres(String genres) {
        if (genres.equals("[]")) {
            return List.of(); // Return an empty list for empty genres
        } else {
            if (genres.startsWith("['") && genres.endsWith("']")) {
                genres = genres.substring(2, genres.length() - 2); // Removing the brackets [ ]
            } else {
                throw new IllegalArgumentException("Invalid genres array in CSV file.");
            }
            return Arrays.stream(genres.split(","))
                .map(word -> word.replaceAll("'", ""))
                .map(word -> word.replaceAll("\\s+", ""))
                .toList();
        }
    }
}
