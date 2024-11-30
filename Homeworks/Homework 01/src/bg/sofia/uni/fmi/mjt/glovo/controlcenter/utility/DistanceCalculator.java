package bg.sofia.uni.fmi.mjt.glovo.controlcenter.utility;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class DistanceCalculator {

    public static int calculateDistance(Location deliveryGuy, Location restaurant,
                                        Location client, MapEntity[][] mapLayout) {
        int toRestaurant = bfsShortestPath(deliveryGuy, restaurant, mapLayout);
        int toClient = bfsShortestPath(restaurant, client, mapLayout);

        if (toRestaurant == Integer.MAX_VALUE || toClient == Integer.MAX_VALUE) {
            // if either path is not reachable, return max value
            return Integer.MAX_VALUE;
        }

        return toRestaurant + toClient;
    }

    private static int bfsShortestPath(Location start, Location end, MapEntity[][] mapLayout) {
        //using the bfs traversal method for finding the shortest path in an unweighted graph
        boolean[][] visited = initializeVisited(mapLayout);
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);

        Map<Location, Integer> distances = new HashMap<>(); //storing the cells and their distance from the start
        distances.put(start, 0);

        while (!queue.isEmpty()) {
            Location current = queue.poll();
            visited[current.x()][current.y()] = true;

            if (current.x() == end.x() && current.y() == end.y()) { //is end reached
                return distances.get(current);
            }

            List<Location> neighboursToCurrent = getNeighbours(current, mapLayout);
            for (Location neighbour : neighboursToCurrent) {
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

    private static boolean[][] initializeVisited(MapEntity[][] mapLayout) {
        int rows = mapLayout.length;

        boolean[][] visited = new boolean[rows][]; // we could have different sized rows
        for (int i = 0; i < rows; i++) {
            visited[i] = new boolean[mapLayout[i].length];
        }

        return visited;
    }

    private static List<Location> getNeighbours(Location current, MapEntity[][] mapLayout) {
        List<Location> neighbours = new ArrayList<>();

        if (isValidMove(current.x() + 1, current.y(), mapLayout)) {
            neighbours.add(mapLayout[current.x() + 1][current.y()].location());
        }
        if (isValidMove(current.x() - 1, current.y(), mapLayout)) {
            neighbours.add(mapLayout[current.x() - 1][current.y()].location());
        }
        if (isValidMove(current.x(), current.y() + 1, mapLayout)) {
            neighbours.add(mapLayout[current.x()][current.y() + 1].location());
        }
        if (isValidMove(current.x(), current.y() - 1, mapLayout)) {
            neighbours.add(mapLayout[current.x()][current.y() - 1].location());
        }
        return neighbours;
    }

    private static boolean isValidMove(int x, int y, MapEntity[][] mapLayout) {
        return x >= 0 && x < mapLayout.length && y >= 0 && y < mapLayout[x].length // check boundaries
            && mapLayout[x][y].type() != MapEntityType.WALL; // avoid walls
    }
}
