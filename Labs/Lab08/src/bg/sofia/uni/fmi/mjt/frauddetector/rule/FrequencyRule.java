package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyRule implements Rule {

    int transactionCountThreshold;
    TemporalAmount timeWindow;
    double weight;

    public FrequencyRule(int transactionCountThreshold, TemporalAmount timeWindow, double weight) {
        this.timeWindow = timeWindow;
        this.transactionCountThreshold = transactionCountThreshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Transactions list is empty.");
        }

        Map<LocalDateTime, Long> transactionsByDate = transactions.stream()
            .collect(Collectors.groupingBy(Transaction::transactionDate, Collectors.counting()));

        List<Map.Entry<LocalDateTime, Long>> sortedEntries = transactionsByDate.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .toList();

        return sortedEntries.stream()
            .anyMatch(entry -> {
                LocalDateTime windowStart = entry.getKey();
                LocalDateTime windowEnd = windowStart.plus(timeWindow);

                long count = sortedEntries.stream()
                    .filter(e -> !e.getKey().isBefore(windowStart) && !e.getKey().isAfter(windowEnd))
                    .mapToLong(Map.Entry::getValue)
                    .sum();

                return count >= transactionCountThreshold;
            });
    }

    @Override
    public double weight() {
        return weight;
    }
}
