package bg.sofia.uni.fmi.mjt.glovo.controlcenter.map;

import bg.sofia.uni.fmi.mjt.glovo.exception.IllegalMapEntityException;

import java.util.HashMap;
import java.util.Map;

public enum MapEntityType {
    ROAD('.'),
    WALL('#'),
    RESTAURANT('R'),
    CLIENT('C'),
    DELIVERY_GUY_CAR('A'),
    DELIVERY_GUY_BIKE('B');

    private static final Map<Character, MapEntityType> SYMBOL_TO_ENTITY_MAP = new HashMap<>();

    static {
        for (MapEntityType type : values()) {
            SYMBOL_TO_ENTITY_MAP.put(type.getSymbol(), type);
        }
    }

    private final char symbol;

    MapEntityType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static MapEntityType fromSymbol(char symbol) {
        if (!SYMBOL_TO_ENTITY_MAP.containsKey(symbol)) {
            throw new IllegalMapEntityException("Invalid symbol in the Map Layout.");
        }
        return SYMBOL_TO_ENTITY_MAP.get(symbol);
    }
}
