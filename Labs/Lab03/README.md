# Bolt: Vehicle Renting at the Tip of Your Fingers ⚡ 🚗 🚲 🚐

One rising trend is the short-term rental of bicycles and cars — be it for a few minutes, hours, or even a full day. Of course, the option still exists for longer rentals spanning days or even weeks.

In this task, we aim to combine both of these approaches by practicing our knowledge of **Records, Sealed Classes, and Exceptions**. Not all of these concepts are explicitly outlined, so think carefully about where each can be applied.

⚠️ **Important:**

- JavaDoc comments above specific methods define the expected functionality.
- Any cases not explicitly mentioned in JavaDocs or the requirements are left to your creativity and judgment.
- The provided hierarchy of classes and methods is not exhaustive and should be further developed with the knowledge gained so far.
- A complete list of exceptions that methods are expected to throw is in their JavaDocs. Not all are listed in method signatures (why might this be?).

---

## RentalService

The vehicle rental platform we will develop is a simple class with the following core structure, which you should expand with implementation and any additional features you find necessary:

```java
package bg.sofia.uni.fmi.mjt.vehiclerent;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.Vehicle;

import java.time.LocalDateTime;

public class RentalService {

    /**
     * Simulates renting of the vehicle. Validates inputs and assigns the provided driver to rent
     * the selected vehicle from the specified start time.
     * @param driver the designated driver
     * @param vehicle the chosen vehicle
     * @param startOfRent the rental start time
     * @throws IllegalArgumentException if any arguments are null
     * @throws VehicleAlreadyRentedException if the vehicle is already rented
     */
    public void rentVehicle(Driver driver, Vehicle vehicle, LocalDateTime startOfRent) {
        throw new UnsupportedOperationException("Dear Student, remove this exception and implement the method.");
    }

    /**
     * Processes rental returns by validating inputs and calculating the total rental price.
     * @param vehicle the rented vehicle
     * @param endOfRent the rental end time
     * @return rental price
     * @throws IllegalArgumentException if any arguments are null
     * @throws VehicleNotRentedException if the vehicle is not currently rented
     * @throws InvalidRentingPeriodException if rental period is invalid (e.g., end is before start)
     */
    public double returnVehicle(Vehicle vehicle, LocalDateTime endOfRent) throws InvalidRentingPeriodException {
        throw new UnsupportedOperationException("Dear Student, remove this exception and implement the method.");
    }
}
```

---

## Driver

Drivers are represented by a simple structure with the following constructor:

```java
public Driver(AgeGroup group) {}
```

`AgeGroup` is the driver's age category, represented by an enumeration with values:

- `JUNIOR`
- `EXPERIENCED`
- `SENIOR`

### **Young Driver Fee**

As you’ll see, `returnVehicle` returns a rental price, which includes an age-based fee for young and senior drivers. This is a common practice among rental companies as younger and older drivers are considered higher risk. In our case, we add a one-time fee to the final rental amount instead of a deposit.

The fees are as follows:

- **JUNIOR**: 10
- **EXPERIENCED**: 0
- **SENIOR**: 15

⚠️ **Note**: Bicycles are exempt from these fees.

---

## Vehicle

A class hierarchy models supported vehicle types: `Vehicle` is the base class with three subclasses: `Bicycle`, `Car`, and `Caravan`. Below is a partial structure for `Vehicle` — complete it with additional functionality.

```java
package bg.sofia.uni.fmi.mjt.vehiclerent.vehicle;

import bg.sofia.uni.fmi.mjt.vehiclerent.driver.Driver;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.InvalidRentingPeriodException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleAlreadyRentedException;
import bg.sofia.uni.fmi.mjt.vehiclerent.exception.VehicleNotRentedException;

import java.time.LocalDateTime;

public class Vehicle {

    public Vehicle(String id, String model) {
        this.id = id;
        this.model = model;
    }

    /**
     * Rents the vehicle to the provided driver at the specified start time.
     * @param driver the driver
     * @param startRentTime the rental start time
     * @throws VehicleAlreadyRentedException if already rented
     */
    public void rent(Driver driver, LocalDateTime startRentTime) {
        throw new UnsupportedOperationException("Dear Student, remove this exception and implement the method.");
    }

    /**
     * Ends the rental for the vehicle.
     * @param rentalEnd rental end time
     * @throws IllegalArgumentException if null
     * @throws VehicleNotRentedException if not rented
     * @throws InvalidRentingPeriodException if rentalEnd is before start date
     */
    public void returnBack(LocalDateTime rentalEnd) throws InvalidRentingPeriodException {
        throw new UnsupportedOperationException("Dear Student, remove this exception and implement the method.");
    }

    /**
     * Calculates potential rental price.
     * @param startOfRent start of rental
     * @param endOfRent end of rental
     * @return rental price
     * @throws InvalidRentingPeriodException if period is invalid
     */
    public abstract double calculateRentalPrice(LocalDateTime startOfRent, LocalDateTime endOfRent) throws InvalidRentingPeriodException;
}
```

---

## Types of Vehicles and Rental Price

The service offers three types of vehicles: bicycles, cars, and caravans. Rental prices vary based on type and additional conditions.

### **Bicycles**
- Rental period: up to 1 week.
- Rental price is defined by hourly and daily rates.

Constructor:

```java
public Bicycle(String id, String model, double pricePerDay, double pricePerHour) {}
```

### **Cars**
- Cars have engines with additional daily fees based on fuel type.
- Prices are defined for hourly, daily, and weekly rentals.

Constructor:

```java
public Car(String id, String model, FuelType fuelType, int numberOfSeats, double pricePerWeek, double pricePerDay, double pricePerHour) {}
```

### **Caravans**
- Caravans have additional seat and bed capacity.
- Rental period: minimum of 1 day.

Constructor:

```java
public Caravan(String id, String model, FuelType fuelType, int numberOfSeats, int numberOfBeds, double pricePerWeek, double pricePerDay, double pricePerHour) {}
```

---

## FuelType Enum

Values and their daily fees:

- **DIESEL**: 3
- **PETROL**: 3
- **HYBRID**: 1
- **ELECTRICITY**: 0
- **HYDROGEN**: 0

---

### **Example Usage**

```java
import bg.sofia.uni.fmi.mjt.vehiclerent.vehicle.FuelType;

public static void main(String[] args) {
    RentalService rentalService = new RentalService();
    LocalDateTime rentStart = LocalDateTime.of(2024, 10, 10, 0, 0, 0);
    Driver experiencedDriver = new Driver(AgeGroup.EXPERIENCED);
   
    Vehicle electricCar = new Car("1", "Tesla Model 3", FuelType.ELECTRICITY, 4, 1000, 150, 10);
    rentalService.rentVehicle(experiencedDriver, electricCar, rentStart);
    double priceToPay = rentalService.returnVehicle(electricCar, rentStart.plusDays(5)); // 770.0
}
```

### **Package Structure**

Ensure all classes and packages follow the structure below:

```
src
└─ bg/sofia/uni/fmi/mjt/vehiclerent
    ├─ driver/
    │  ├─ AgeGroup.java
    │  └─ Driver.java
    ├─ exception/
    │  ├─ InvalidRentingPeriodException.java
    │  ├─ VehicleAlreadyRentedException.java
    │  └─ VehicleNotRentedException.java
    ├─ vehicle/
    │  ├─ Bicycle.java
    │  ├─ Car.java
    │  ├─ Caravan.java
    │  ├─ FuelType.java
    │  └─ Vehicle.java   
    └─ RentalService.java
```
