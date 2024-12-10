package bg.sofia.uni.fmi.mjt.frauddetector.transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record Transaction(String transactionID, String accountID, double transactionAmount,
                          LocalDateTime transactionDate, String location, Channel channel) {

    private static final String PEAK_ATTRIBUTE_DELIMITER = ",";
    private static final int ATTRIBUTE_COUNT = 6;

    private static final int FIRST_COLUMN = 0;
    private static final int SECOND_COLUMN = 1;
    private static final int THIRD_COLUMN = 2;
    private static final int FOURTH_COLUMN = 3;
    private static final int FIFTH_COLUMN = 4;
    private static final int SIXTH_COLUMN = 5;

    public static Transaction of(String line) {
        if (line == null || line.isEmpty()) {
            throw new IllegalArgumentException("Line of transaction is empty.");
        }

        final String[] tokens = line.split(PEAK_ATTRIBUTE_DELIMITER);

        if (tokens.length < ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException("Line of transaction is inconsistent.");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime date = LocalDateTime.parse(tokens[FOURTH_COLUMN], formatter);

            return new Transaction(
                tokens[FIRST_COLUMN],
                tokens[SECOND_COLUMN],
                Double.parseDouble(tokens[THIRD_COLUMN]),
                date,
                tokens[FIFTH_COLUMN],
                Channel.of(tokens[SIXTH_COLUMN])
            );

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse transaction.", e);
        }
    }
}
