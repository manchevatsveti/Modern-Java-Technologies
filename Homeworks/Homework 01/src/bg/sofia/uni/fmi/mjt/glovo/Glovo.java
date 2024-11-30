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

import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.utility.ValidationUtils.validateObjectIsNotNull;

public class Glovo implements GlovoApi {

    private static final int NO_CONSTRAINT_TIME = -1;
    private static final double NO_CONSTRAINT_PRICE = -1;

    private final ControlCenter controlCenter;

    public Glovo(char[][] mapLayout) {
        this.controlCenter = new ControlCenter(mapLayout); //validation is handled in MapBuilder class
    }

    @Override
    public Delivery getCheapestDelivery(MapEntity client, MapEntity restaurant, String food)
        throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, food, ShippingMethod.CHEAPEST,
            NO_CONSTRAINT_PRICE, NO_CONSTRAINT_TIME);
    }

    @Override
    public Delivery getFastestDelivery(MapEntity client, MapEntity restaurant, String food)
        throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, food, ShippingMethod.FASTEST,
            NO_CONSTRAINT_PRICE, NO_CONSTRAINT_TIME);
    }

    @Override
    public Delivery getFastestDeliveryUnderPrice(MapEntity client, MapEntity restaurant, String food,
                                                 double maxPrice) throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, food, ShippingMethod.FASTEST, maxPrice, NO_CONSTRAINT_TIME);
    }

    @Override
    public Delivery getCheapestDeliveryWithinTimeLimit(MapEntity client, MapEntity restaurant, String food,
                                                       int maxTime) throws NoAvailableDeliveryGuyException {
        return processDelivery(client, restaurant, food, ShippingMethod.CHEAPEST, NO_CONSTRAINT_PRICE, maxTime);
    }

    private Delivery processDelivery(MapEntity client, MapEntity restaurant, String food,
                                     ShippingMethod method, double maxPrice, int maxTime)
        throws NoAvailableDeliveryGuyException {

        validateMapEntity(client, MapEntityType.CLIENT, "Invalid client MapEntity.");
        validateMapEntity(restaurant, MapEntityType.RESTAURANT, "Invalid restaurant MapEntity.");

        DeliveryInfo deliveryInfo = controlCenter.findOptimalDeliveryGuy(restaurant.location(),
            client.location(), maxPrice, maxTime, method);

        if (deliveryInfo == null) {
            throw new NoAvailableDeliveryGuyException("No available delivery guy.");
        }

        return new Delivery(client.location(), restaurant.location(), deliveryInfo.deliveryGuyLocation(),
            food, deliveryInfo.price(), deliveryInfo.estimatedTime());
    }

    private void validateMapEntity(MapEntity entity, MapEntityType expectedType, String errorMessage) {
        validateObjectIsNotNull(entity, errorMessage);

        Location location = entity.location();
        if (!isInDefinedBoundaries(location)) {
            throw new InvalidOrderException(errorMessage);
        }

        if (!isExpectedType(entity, expectedType)) {
            throw new InvalidOrderException(errorMessage);
        }
    }

    private boolean isExpectedType(MapEntity entity, MapEntityType expectedType) {
        Location location = entity.location();
        int x = location.x();
        int y = location.y();
        MapEntity[][] map = controlCenter.getLayout();
        return map[x][y].type() == expectedType;
    }

    private boolean isInDefinedBoundaries(Location location) {
        int x = location.x();
        int y = location.y();
        MapEntity[][] map = controlCenter.getLayout();
        return x >= 0 && x < map.length && y >= 0 && y < map[x].length;
    }
}
