package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import bg.sofia.uni.fmi.mjt.glovo.exception.MapInitializationException;

public class MapBuilder {

    public static MapEntity[][] buildMapFromLayout(char[][] mapLayout) {
        validateMapLayout(mapLayout);
        return initializeEntities(mapLayout);
    }

    private static void validateMapLayout(char[][] mapLayout) {
        if (mapLayout == null || mapLayout.length == 0) {
            throw new MapInitializationException("Map layout cannot be null or empty.");
        }
    }

    private static MapEntity[][] initializeEntities(char[][] mapLayout) {
        int rows = mapLayout.length;
        MapEntity[][] entities = new MapEntity[rows][];

        for (int i = 0; i < rows; i++) {
            entities[i] = initializeRow(mapLayout[i], i);
        }
        return entities;
    }

    private static MapEntity[] initializeRow(char[] row, int rowIndex) {
        int cols = row.length;
        MapEntity[] entities = new MapEntity[cols];
        for (int j = 0; j < cols; j++) {
            Location location = new Location(rowIndex, j);
            entities[j] = new MapEntity(location, MapEntityType.fromSymbol(row[j]));
        }
        return entities;
    }
}
