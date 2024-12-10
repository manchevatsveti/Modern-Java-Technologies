package bg.sofia.uni.fmi.mjt.frauddetector.rule;

import bg.sofia.uni.fmi.mjt.frauddetector.transaction.Transaction;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class LocationsRule implements Rule {

    int threshold;
    double weight;

    public LocationsRule(int threshold, double weight) {
        this.threshold = threshold;
        this.weight = weight;
    }

    @Override
    public boolean applicable(List<Transaction> transactions) {
        Set<String> uniqueLocations = transactions.stream()
            .map(Transaction::location)
            .collect(Collectors.toSet());

        return uniqueLocations.size() >= threshold;
    }

    @Override
    public double weight() {
        return weight;
    }
}
