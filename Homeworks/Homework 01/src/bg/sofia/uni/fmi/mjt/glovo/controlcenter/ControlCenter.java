package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapBuilder;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class ControlCenter implements ControlCenterApi {

    private static final double NO_CONSTRAINT = -1;

    private final MapEntity[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = MapBuilder.buildMapFromLayout(mapLayout);
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation,
                                               double maxPrice, int maxTime, ShippingMethod method) {

        validateObjectNotNull(restaurantLocation, "Restaurant location is null.");
        validateObjectNotNull(clientLocation, "Client location is null.");
        validateObjectNotNull(method, "Shipping method is null.");

        DeliveryInfo bestDelivery = null;

        for (MapEntity[] row : mapLayout) {
            for (MapEntity entity : row) {
                if (isDeliveryGuy(entity)) {

                    //it creates a delivery only if the constraints are met
                    DeliveryInfo tempDelivery = createDeliveryInfo(entity, restaurantLocation, clientLocation,
                        maxPrice, maxTime);

                    if (tempDelivery != null) {
                        if (bestDelivery == null || isBetterDeliveryOption(tempDelivery, bestDelivery, method)) {
                            //if there is no bestDelivery or if we found better delivery option, we change it
                            bestDelivery = tempDelivery;
                        }
                    }
                }
            }
        }
        return bestDelivery;
    }

    private boolean isDeliveryGuy(MapEntity entity) {
        return entity.type() == MapEntityType.DELIVERY_GUY_CAR ||
            entity.type() == MapEntityType.DELIVERY_GUY_BIKE;
    }

    private DeliveryInfo createDeliveryInfo(MapEntity entity, Location restaurant, Location client,
                                            double maxPrice, int maxTime) {

        DeliveryType type = entity.type() == MapEntityType.DELIVERY_GUY_CAR ? DeliveryType.CAR : DeliveryType.BIKE;

        int distance = calculateTotalDistance(entity.location(), restaurant, client);

        if (distance == Integer.MAX_VALUE) {
            // if no valid path exists, we skip this delivery guy
            return null;
        }

        double price = distance * type.getPricePerKm();
        int time = distance * type.getTimePerKm();

        if ((maxPrice == NO_CONSTRAINT || price <= maxPrice) //comparing doubles
            && (maxTime == NO_CONSTRAINT || time <= maxTime)) {
            return new DeliveryInfo(entity.location(), price, time, type);
        }

        //if the delivery guy is not able to deliver under given constraints
        return null;
    }

    private boolean isBetterDeliveryOption(DeliveryInfo temp, DeliveryInfo currentBest, ShippingMethod method) {
        return switch (method) {
            case CHEAPEST -> isCheaper(temp.price(), currentBest.price())
                || (arePricesEqual(temp.price(), currentBest.price()) &&
                isFaster(temp.estimatedTime(), currentBest.estimatedTime()));

            case FASTEST -> isFaster(temp.estimatedTime(), currentBest.estimatedTime())
                || (areTimesEqual(temp.estimatedTime(), currentBest.estimatedTime()) &&
                isCheaper(temp.price(), currentBest.price()));

        };
    }

    private boolean isCheaper(double newPrice, double currentPrice) {

        return newPrice < currentPrice;
    }

    private boolean arePricesEqual(double price1, double price2) {

        return Double.compare(price1, price2) == 0;
    }

    private boolean isFaster(int newTime, int currentTime) {

        return newTime < currentTime;
    }

    private boolean areTimesEqual(int time1, int time2) {

        return time1 == time2;
    }

    private int calculateTotalDistance(Location deliveryGuy, Location restaurant, Location client) {
        int toRestaurant = bfsShortestPath(deliveryGuy, restaurant);
        int toClient = bfsShortestPath(restaurant, client);

        if (toRestaurant == Integer.MAX_VALUE || toClient == Integer.MAX_VALUE) {
            // if either path is not reachable, return max value
            return Integer.MAX_VALUE;
        }

        return toRestaurant + toClient;
    }

    public static <T> void validateObjectNotNull(T obj, String errorMssg) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMssg);
        }
    }

    private int bfsShortestPath(Location start, Location end) {
        //using the bfs traversal method for finding the shortest path in an unweighted graph

        boolean[][] visited = initializeVisited();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);

        Map<Location, Integer> distances = new HashMap<>(); //storing the cells and their distance from the start
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            visited[current.x()][current.y()] = true;

            if (isTarget(current, end)) {
                return distances.get(current);
            }

            for (Location neighbour : getNeighbours(current)) {
                if (!visited[neighbour.x()][neighbour.y()]) {
                    queue.add(neighbour);
                    visited[neighbour.x()][neighbour.y()] = true;

                    int currentDistance = distances.get(current);
                    distances.put(neighbour, currentDistance + 1);
                }
            }
        }
        return Integer.MAX_VALUE; // no path found
    }

    private boolean[][] initializeVisited() {
        int rows = mapLayout.length;

        boolean[][] visited = new boolean[rows][]; // we could have different sized rows
        for (int i = 0; i < rows; i++) {
            visited[i] = new boolean[mapLayout[i].length];
        }

        return visited;
    }

    private boolean isTarget(Location current, Location end) {
        return current.x() == end.x() && current.y() == end.y();
    }

    private List<Location> getNeighbours(Location current) {
        List<Location> neighbours = new ArrayList<>();

        if (isValidMove(current.x() + 1, current.y())) {
            neighbours.add(mapLayout[current.x() + 1][current.y()].location());
        }
        if (isValidMove(current.x() - 1, current.y())) {
            neighbours.add(mapLayout[current.x() - 1][current.y()].location());
        }
        if (isValidMove(current.x(), current.y() + 1)) {
            neighbours.add(mapLayout[current.x()][current.y() + 1].location());
        }
        if (isValidMove(current.x(), current.y() - 1)) {
            neighbours.add(mapLayout[current.x()][current.y() - 1].location());
        }
        return neighbours;
    }

    private boolean isValidMove(int nx, int ny) {
        return nx >= 0 && nx < mapLayout.length && ny >= 0 && ny < mapLayout[nx].length // check boundaries
            && mapLayout[nx][ny].type() != MapEntityType.WALL; // avoid walls
    }

    @Override
    public MapEntity[][] getLayout() {
        return mapLayout;
    }
}
