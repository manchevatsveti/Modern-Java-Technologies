package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

final public class Caravan extends Vehicle{
    private final FuelType fuelType;
    private final int numberOfSeats;
    private final int numberOfBeds;
    private final double pricePerWeek;
    private final double pricePerDay;
    private final double pricePerHour;

    private static final Duration MIN_RENTAL_PERIOD = Duration.ofHours(23).plusMinutes(59).plusSeconds(59);
    private static final int SEAT_TAX = 5;
    private static final int BED_TAX = 10;
    private static final int DAYS_IN_WEEK = 7;

    public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.numberOfBeds = numberOfBeds;
        this.pricePerWeek = pricePerWeek;
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);

        //Validate rental period (minimum one day)
        if (rentalDuration.compareTo(MIN_RENTAL_PERIOD) < 0) {
            throw new InvalidRentingPeriodException("Rental period should be at least one day.");
        }

        long totalDays = rentalDuration.toDays();
        long weeks = totalDays / DAYS_IN_WEEK;
        long days = totalDays % DAYS_IN_WEEK;
        long hours = rentalDuration.minusDays(totalDays).toHours();

        double rentalCost = (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour);
        double seatTax = numberOfSeats * SEAT_TAX;
        double bedTax = numberOfBeds * BED_TAX;
        double fuelTax = totalDays * fuelType.getDailyTax();

        double ageTax = calculateAgeTax(totalDays);
        return rentalCost + seatTax + bedTax + fuelTax + ageTax;
    }
}
