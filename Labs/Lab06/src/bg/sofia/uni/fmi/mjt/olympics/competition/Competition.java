package bg.sofia.uni.fmi.mjt.olympics.competition;

import bg.sofia.uni.fmi.mjt.olympics.competitor.Competitor;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public record Competition(String name, String discipline, Set<Competitor> competitors) {

    public Competition(String name, String discipline, Set<Competitor> competitors) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Competition name cannot be null or blank");
        }
        if (discipline == null || discipline.isBlank()) {
            throw new IllegalArgumentException("Discipline cannot be null or blank");
        }
        if (competitors == null || competitors.isEmpty()) {
            throw new IllegalArgumentException("Competitors cannot be null or empty");
        }

        this.name = name;
        this.discipline = discipline;
        this.competitors = competitors;
    }

    public Set<Competitor> competitors() {
        return Collections.unmodifiableSet(competitors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return Objects.equals(name, that.name) && Objects.equals(discipline, that.discipline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, discipline);
    }
}
