package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;

import java.time.Duration;
import java.time.LocalDateTime;

public final class Car extends Vehicle{
    private final FuelType fuelType;
    private final int numberOfSeats;
    private final double pricePerWeek;
    private final double pricePerDay;
    private final double pricePerHour;

    private static final int SEAT_TAX = 5;
    private static final int DAYS_IN_WEEK = 7;

    public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {
        super(id, model);
        this.fuelType = fuelType;
        this.numberOfSeats = numberOfSeats;
        this.pricePerDay = pricePerDay;
        this.pricePerWeek = pricePerWeek;
        this.pricePerHour = pricePerHour;
    }

    @Override
    public double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) {
        Duration rentalDuration = Duration.between(startOfRent, endOfRent);

        if (rentalDuration.toMinutes() <= MAX_MINUTES_IN_HOUR) {
            return pricePerHour;
        }

        long totalDays = rentalDuration.toDays();
        long weeks = totalDays / DAYS_IN_WEEK;
        long days = totalDays % DAYS_IN_WEEK;
        long hours = rentalDuration.minusDays(totalDays).toHours();

        double rentalCost = (weeks * pricePerWeek) + (days * pricePerDay) + (hours * pricePerHour);
        double seatTax = numberOfSeats * SEAT_TAX;
        double fuelTax = totalDays * fuelType.getDailyTax();

        double ageTax = calculateAgeTax(totalDays);

        return rentalCost + seatTax + fuelTax + ageTax;
    }
}
