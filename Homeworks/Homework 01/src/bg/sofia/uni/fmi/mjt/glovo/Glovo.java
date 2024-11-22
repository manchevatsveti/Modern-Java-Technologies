package bg.sofia.uni.fmi.mjt.glovo;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.ControlCenter;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.Delivery;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;
import bg.sofia.uni.fmi.mjt.glovo.exception.InvalidOrderException;
import bg.sofia.uni.fmi.mjt.glovo.exception.NoAvailableDeliveryGuyException;

public class Glovo implements GlovoApi {

    private static final double NO_CONSTRAINT = -1;

    private final ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {
        if (mapLayout == null || mapLayout.length == 0) {
            throw new IllegalArgumentException("Map layout cannot be null or empty.");
        }
        this.controlCenter = new ControlCenter(mapLayout);
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, foodItem, ShippingMethod.CHEAPEST,
            NO_CONSTRAINT, (int)NO_CONSTRAINT);
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String foodItem)
        throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, foodItem, ShippingMethod.FASTEST, NO_CONSTRAINT, (int)NO_CONSTRAINT);
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String foodItem,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, foodItem, ShippingMethod.FASTEST, maxPrice, (int)NO_CONSTRAINT);
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String foodItem,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, foodItem, ShippingMethod.CHEAPEST, NO_CONSTRAINT, maxTime);
    }

    private Delivery processDelivery(MapEntity client, MapEntity restaurant, String foodItem,
                                     ShippingMethod method, double maxPrice, int maxTime)
        throws NoAvailableDeliveryGuyException {

        validateEntity(client, MapEntityType.CLIENT, "Invalid client MapEntity.");
        validateEntity(restaurant, MapEntityType.RESTAURANT, "Invalid restaurant MapEntity.");

        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(
            restaurant.location(), client.location(), maxPrice, maxTime, method
        );

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy for this task.");
        }

        return createDelivery(client, restaurant, foodItem, deliveryInfo);
    }

    private void validateEntity(MapEntity entity, MapEntityType expectedType, String errorMessage) {
        if (entity == null || entity.location() == null) {
            throw new InvalidOrderException(errorMessage + " MapEntity or its location cannot be null.");
        }
        Location location = entity.location();
        if (!isInDefinedBoundaries(location)) {
            throw new InvalidOrderException(errorMessage + " Location out of bounds: " + location);
        }
        if (!validateLocationType(entity, expectedType)) {
            throw new InvalidOrderException(errorMessage + " Expected type: " + expectedType);
        }
    }

    private boolean validateLocationType(MapEntity entity, MapEntityType type) {
        Location location = entity.location();
        int x = location.x();
        int y = location.y();
        MapEntity[][] map = controlCenter.getLayout();
        return map[x][y].type() == type;
    }

    private boolean isInDefinedBoundaries(Location location) {
        int x = location.x();
        int y = location.y();
        MapEntity[][] map = controlCenter.getLayout();
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
    }

    private Delivery createDelivery(MapEntity client, MapEntity restaurant, String foodItem, DeliveryInfo info) {
        return new Delivery(
            client.location(),
            restaurant.location(),
            info.deliveryGuyLocation(),
            foodItem,
            info.price(),
            info.estimatedTime()
        );
    }
}
