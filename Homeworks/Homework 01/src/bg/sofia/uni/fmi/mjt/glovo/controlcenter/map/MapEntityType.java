package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import bg.sofia.uni.fmi.mjt.glovo.exception.IllegalMapEntityException;

public enum MapEntityType {
    ROAD('.'),
    WALL('#'),
    RESTAURANT('R'),
    CLIENT('C'),
    DELIVERY_GUY_CAR('A'),
    DELIVERY_GUY_BIKE('B');

    private final char symbol;

    MapEntityType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static MapEntityType fromSymbol(char symbol) {

        for (MapEntityType temp : values()) {
            if (symbol == temp.getSymbol()) {
                return temp;
            }
        }
        throw new IllegalMapEntityException("Invalid symbol to convert to MapEntity.");
    }
}
