package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;

public class ZScoreRule implements Rule {

    double zScoreThreshold;
    double weight;

    public ZScoreRule(double zScoreThreshold, double weight) {
        this.weight = weight;
        this.zScoreThreshold = zScoreThreshold;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }

        double average = getAverage(transactions);

        double var = getVariance(transactions, average);
        double standardDeviation  = Math.sqrt(var);

        if (standardDeviation == 0) {
            return false;
        }

        return transactions.stream()
            .anyMatch(transaction ->
                (transaction.transactionAmount() - average) / standardDeviation >= zScoreThreshold);
    }

    private double getVariance(List<Transaction> transactions, double average) {
        double sum = transactions.stream()
            .mapToDouble(transaction -> Math.pow(transaction.transactionAmount() - average, 2))
            .sum();

        double countTransactions = transactions.size();

        return sum / countTransactions;
    }

    private double getAverage(List<Transaction> transactions) {
        double sum = transactions.stream()
            .mapToDouble(Transaction::transactionAmount)
            .sum();

        double countTransactions = transactions.size();

        return sum / countTransactions;
    }

    @Override
    public double weight() {
        return weight;
    }
}
