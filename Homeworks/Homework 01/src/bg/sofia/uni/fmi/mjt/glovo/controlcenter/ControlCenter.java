package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapBuilder;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

import java.util.LinkedList;
import java.util.Queue;

public class ControlCenter implements ControlCenterApi {

    private static final double NO_CONSTRAINT = -1;
    private static final int DIRECTIONS_COUNT = 4;

    private final MapEntity[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = MapBuilder.buildMapFromLayout(mapLayout);
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation,
                                               double maxPrice, int maxTime, ShippingMethod method) {
        validateInputs(restaurantLocation, clientLocation, method);

        DeliveryInfo bestDelivery = null;

        for (MapEntity[] row : mapLayout) {
            for (MapEntity entity : row) {
                if (isDeliveryGuy(entity)) {
                    DeliveryInfo candidate = createDeliveryInfo(entity, restaurantLocation, clientLocation,
                        maxPrice, maxTime, method, bestDelivery);

                    if (candidate != null) {
                        bestDelivery = candidate;
                    }
                }
            }
        }
        return bestDelivery;
    }

    private boolean isDeliveryGuy(MapEntity entity) {
        return entity != null && (entity.type() == MapEntityType.DELIVERY_GUY_CAR ||
            entity.type() == MapEntityType.DELIVERY_GUY_BIKE);
    }

    private DeliveryInfo createDeliveryInfo(MapEntity entity, Location restaurant, Location client, double maxPrice,
                                            int maxTime, ShippingMethod method, DeliveryInfo currentBest) {

        DeliveryType type = entity.type() == MapEntityType.DELIVERY_GUY_CAR ? DeliveryType.CAR : DeliveryType.BIKE;
        int distance = calculateTotalDistance(entity.location(), restaurant, client);

        if (distance == Integer.MAX_VALUE) {
            // If no valid path exists, skip this delivery guy
            return null;
        }

        double price = distance * type.getPricePerKm();
        int time = distance * type.getTimePerKm();

        if ((maxPrice == NO_CONSTRAINT || price <= maxPrice) //compare doubles
            && (maxTime == NO_CONSTRAINT || time <= maxTime)) {
            if ((currentBest == null || isBetterCandidate(currentBest, price, time, method))) {
                return new DeliveryInfo(entity.location(), price, time, type);
            }
        }

        //If no delivery guy is able to deliver under given constraints
        return null;
    }

    private boolean isBetterCandidate(DeliveryInfo current, double newPrice, int newTime, ShippingMethod method) {
        return method == ShippingMethod.CHEAPEST ? newPrice < current.price() : newTime < current.estimatedTime();
    }

    private int calculateTotalDistance(Location deliveryGuy, Location restaurant, Location client) {
        int toRestaurant = bfsShortestPath(deliveryGuy, restaurant);
        int toClient = bfsShortestPath(restaurant, client);

        if (toRestaurant == Integer.MAX_VALUE || toClient == Integer.MAX_VALUE) {
            // If either path is not reachable, return max value
            return Integer.MAX_VALUE;
        }

        return toRestaurant + toClient;
    }

    private void validateInputs(Location restaurant, Location client, ShippingMethod method) {
        if (restaurant == null || client == null || method == null) {
            throw new IllegalArgumentException("Invalid arguments: locations or shipping method cannot be null.");
        }
    }

    private int bfsShortestPath(Location start, Location end) {
        int rows = mapLayout.length;
        int cols = mapLayout[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = initializeQueue(start, visited);

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            if (isTarget(current, end)) {
                return current[2];
            }
            addNeighborsToQueue(queue, visited, current, rows, cols);
        }

        return Integer.MAX_VALUE; // No path found
    }

    private Queue<int[]> initializeQueue(Location start, boolean[][] visited) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start.x(), start.y(), 0});
        visited[start.x()][start.y()] = true;
        return queue;
    }

    private boolean isTarget(int[] current, Location end) {
        return current[0] == end.x() && current[1] == end.y();
    }

    private void addNeighborsToQueue(Queue<int[]> queue, boolean[][] visited, int[] current, int rows, int cols) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < DIRECTIONS_COUNT; i++) {
            int nx = current[0] + dx[i];
            int ny = current[1] + dy[i];

            if (isValidMove(nx, ny, rows, cols, visited)) {
                queue.offer(new int[]{nx, ny, current[2] + 1});
                visited[nx][ny] = true;
            }
        }
    }

    private boolean isValidMove(int nx, int ny, int rows, int cols, boolean[][] visited) {
        return nx >= 0 && nx < rows && ny >= 0 && ny < cols // Check boundaries
            && !visited[nx][ny]
            && mapLayout[nx][ny].type() != MapEntityType.WALL; // Avoid walls
    }

    @Override
    public MapEntity[][] getLayout() {
        return mapLayout;
    }
}
