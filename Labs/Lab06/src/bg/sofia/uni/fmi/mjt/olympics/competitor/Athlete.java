package bg.sofia.uni.fmi.mjt.olympics.competitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Athlete implements Competitor {

    private final String identifier;
    private final String name;
    private final String nationality;

    private final EnumMap<Medal, Integer> medals;

    public Athlete(String identifier, String name, String nationality) {
        this.identifier = identifier;
        this.name = name;
        this.nationality = nationality;
        this.medals = new EnumMap<>(Medal.class);
    }

    public void addMedal(Medal medal) {
        validateMedal(medal);
        medals.putIfAbsent(medal, 0);
        medals.put(medal, medals.getOrDefault(medal, 0) + 1);
    }

    public void validateMedal(Medal medal) {
        if (medal == null) {
            throw new IllegalArgumentException("Medal cannot be null");
        }
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNationality() {
        return nationality;
    }

    @Override
    public Collection<Medal> getMedals() {
        List<Medal> result = new ArrayList<>();
        for (Map.Entry<Medal, Integer> entry : medals.entrySet()) {
            result.addAll(Collections.nCopies(entry.getValue(), entry.getKey()));
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Athlete athlete = (Athlete) o;
        return Objects.equals(name, athlete.name) &&
            Objects.equals(nationality, athlete.nationality) && Objects.equals(medals, athlete.medals);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
