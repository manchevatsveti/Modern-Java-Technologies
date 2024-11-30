package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import bg.sofia.uni.fmi.mjt.glovo.exception.MapInitializationException;

public class MapBuilder {

    public static MapEntity[][] buildMapFromLayout(char[][] mapLayout) {
        if (mapLayout == null || mapLayout.length == 0) {
            throw new MapInitializationException("Map layout cannot be null or empty.");
        }

        int rows = mapLayout.length;

        MapEntity[][] entities = new MapEntity[rows][];

        for (int i = 0; i < rows; i++) {

            int cols = mapLayout[i].length; //we could have different sized rows - jagged array

            entities[i] = new MapEntity[cols];

            for (int j = 0; j < cols; j++) {
                Location location = new Location(i, j);
                entities[i][j] = new MapEntity(location, MapEntityType.fromSymbol(mapLayout[i][j]));
            }
        }
        return entities;
    }
}
