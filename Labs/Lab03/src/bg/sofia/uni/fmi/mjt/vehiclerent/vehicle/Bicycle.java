package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

final public class Bicycle extends Vehicle{
    private final double pricePerDay;
    private final double pricePerHour;

    private static final Duration MAX_RENTAL_PERIOD = Duration.ofDays(6).plusHours(23).plusMinutes(59).plusSeconds(59);

    public Bicycle(String id, String model, double pricePerDay, double pricePerHour){
        super(id, model);
        this.pricePerDay = pricePerDay;
        this. pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);

        if (rentalDuration.compareTo(MAX_RENTAL_PERIOD) > 0) {
            throw new InvalidRentingPeriodException("Rental period exceeds the maximum allowed duration of 6 days, 23 hours, 59 minutes, and 59 seconds.");
        }

        if(rentalDuration.toMinutes() <= MAX_MINUTES_IN_HOUR){
            return pricePerHour;
        }

        long days = rentalDuration.toDays();
        long hours = rentalDuration.minusDays(days).toHours();

        return (days * pricePerDay) + (hours * pricePerHour);
    }
}
