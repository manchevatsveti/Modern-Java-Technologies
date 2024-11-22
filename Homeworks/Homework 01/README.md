# Glovo 🚚

## Task Description  
Imagine a world where food and groceries arrive at your doorstep with a single click. Now picture yourself as the magician behind this magic! ✨ Your mission is to develop a delivery application that tracks orders, ensures timely arrivals, and brings happiness to people's doors. 🚀🍕🥡  

## Objectives  
- Implement two provided interfaces, **GlovoApi** and **ControlCenterApi**.  
- Follow the provided method signatures and constructors without modification.  
- Add custom classes if necessary for your solution.  
- Introduce at least **two custom runtime exceptions** that fit the context of your implementation.  
- Validate all input parameters in your methods.  
- Select the most appropriate algorithms for the task, optimizing for efficiency where possible.  

## Details  

### Map Layout  
You will work with a 2D map (`char[][]`) where each cell has a specific meaning:  
| Symbol | Meaning             |  
|--------|---------------------|  
| `#`    | Wall                |  
| `.`    | Road                |  
| `R`    | Restaurant          |  
| `C`    | Client              |  
| `A`    | Delivery Guy (Car)  |  
| `B`    | Delivery Guy (Bike) |  

Movement directions: **up, down, left, right**. Delivery guys can move through any cell except walls (`#`).  

### Example Map Layout  
```java
char[][] layout = {
    {'#', '#', '#', '.', '#'},
    {'#', '.', 'B', 'R', '.'},
    {'.', '.', '#', '.', '#'},
    {'#', 'C', '.', 'A', '.'},
    {'#', '.', '#', '#', '#'}
};
```  

### Provided Interfaces  

#### **GlovoApi**  
Defines methods for managing deliveries:  
```java
Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem) 
    throws NoAvailableDeliveryGuyException;

Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem) 
    throws NoAvailableDeliveryGuyException;

Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem, double maxPrice) 
    throws NoAvailableDeliveryGuyException;

Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem, int maxTime) 
    throws NoAvailableDeliveryGuyException;
```  

#### **ControlCenterApi**  
Provides tools to manage and optimize delivery logistics:  
```java
DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation, 
                                    double maxPrice, int maxTime, ShippingMethod shippingMethod);

MapEntity[][] getLayout();
```  

---

## Supporting Types  

### **Location**  
Represents the coordinates of an entity on the map:  
```java
public Location(int x, int y);
```  

### **MapEntityType**  
Enum for entity types on the map:  
| Type              | Symbol |  
|-------------------|--------|  
| `ROAD`           | `.`    |  
| `WALL`           | `#`    |  
| `RESTAURANT`     | `R`    |  
| `CLIENT`         | `C`    |  
| `DELIVERY_GUY_CAR`| `A`    |  
| `DELIVERY_GUY_BIKE`| `B`   |  

### **MapEntity**  
Represents an entity on the map:  
```java
public MapEntity(Location location, MapEntityType type);
```  

### **Delivery**  
Defines a delivery task:  
```java
public Delivery(Location client, Location restaurant, Location deliveryGuy, 
                String foodItem, double price, int estimatedTime);
```  

### **DeliveryType**  
Enum for delivery methods with respective costs and time:  
| Type  | Cost/Km | Time/Km |  
|-------|---------|---------|  
| `CAR` | 5       | 3       |  
| `BIKE`| 3       | 5       |  

### **ShippingMethod**  
Defines delivery prioritization:  
```java
public enum ShippingMethod {
    FASTEST, CHEAPEST;
}
```  

### **DeliveryInfo**  
Details about an optimized delivery task:  
```java
public DeliveryInfo(Location deliveryGuyLocation, double price, int estimatedTime, DeliveryType deliveryType);
```  

---

## Project Structure  

```
src
└── bg.sofia.uni.fmi.mjt.glovo
    ├── controlcenter
    │   ├── map
    │   │   ├── Location.java
    │   │   ├── MapEntity.java
    │   │   ├── MapEntityType.java
    │   │   └── (...)
    │   ├── ControlCenterApi.java
    │   ├── ControlCenter.java
    │   └── (...)
    ├── delivery
    │   ├── Delivery.java
    │   ├── DeliveryInfo.java
    │   ├── DeliveryType.java
    │   ├── ShippingMethod.java
    │   └── (...)
    ├── exception
    │   ├── InvalidOrderException.java
    │   ├── NoAvailableDeliveryGuyException.java
    │   └── (...)
    ├── Glovo.java
    ├── GlovoApi.java
    └── (...)
```  

---

## Notes  
- Validate all inputs and handle exceptions appropriately.  
- Optimize algorithms for better performance where feasible.  

