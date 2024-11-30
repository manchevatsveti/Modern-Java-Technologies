package bg.sofia.uni.fmi.mjt.glovo.controlcenter;

import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.Location;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapBuilder;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntity;
import bg.sofia.uni.fmi.mjt.glovo.controlcenter.map.MapEntityType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryInfo;
import bg.sofia.uni.fmi.mjt.glovo.delivery.DeliveryType;
import bg.sofia.uni.fmi.mjt.glovo.delivery.ShippingMethod;

import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.utility.DistanceCalculator.calculateDistance;
import static bg.sofia.uni.fmi.mjt.glovo.controlcenter.utility.ValidationUtils.validateObjectIsNotNull;

public class ControlCenter implements ControlCenterApi {

    private static final int NO_CONSTRAINT_TIME = -1;
    private static final double NO_CONSTRAINT_PRICE = -1;

    private final MapEntity[][] mapLayout;

    public ControlCenter(char[][] mapLayout) {
        this.mapLayout = MapBuilder.buildMapFromLayout(mapLayout);
    }

    @Override
    public DeliveryInfo findOptimalDeliveryGuy(Location restaurantLocation, Location clientLocation,
                                               double maxPrice, int maxTime, ShippingMethod method) {

        validateObjectIsNotNull(restaurantLocation, "Restaurant location is null.");
        validateObjectIsNotNull(clientLocation, "Client location is null.");
        validateObjectIsNotNull(method, "Shipping method is null.");

        DeliveryInfo bestDelivery = null;

        for (MapEntity[] row : mapLayout) {
            for (MapEntity entity : row) {
                if (isDeliveryGuy(entity)) { //if there aren't delivery guys on the map, bestDelivery remains null

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

    private static boolean isDeliveryGuy(MapEntity entity) {
        return entity.type() == MapEntityType.DELIVERY_GUY_CAR ||
            entity.type() == MapEntityType.DELIVERY_GUY_BIKE;
    }

    private DeliveryInfo createDeliveryInfo(MapEntity entity, Location restaurant, Location client,
                                            double maxPrice, int maxTime) {

        int distance = calculateDistance(entity.location(), restaurant, client, mapLayout);

        if (distance == Integer.MAX_VALUE) {
            // if no valid path exists, we skip this delivery guy
            return null;
        }

        DeliveryType type = entity.type() == MapEntityType.DELIVERY_GUY_CAR ? DeliveryType.CAR : DeliveryType.BIKE;

        double price = distance * type.getPricePerKm();
        int time = distance * type.getTimePerKm();

        if ((maxPrice == NO_CONSTRAINT_PRICE || price <= maxPrice)
            && (maxTime == NO_CONSTRAINT_TIME || time <= maxTime)) {
            return new DeliveryInfo(entity.location(), price, time, type);
        }

        //if the delivery guy is not able to deliver under given constraints
        return null;
    }

    private boolean isBetterDeliveryOption(DeliveryInfo temp, DeliveryInfo currentBest, ShippingMethod method) {
        return switch (method) {
            case CHEAPEST -> (temp.price() < currentBest.price())
                || ((temp.price() == currentBest.price()) &&
                (temp.estimatedTime() < currentBest.estimatedTime()));

            case FASTEST -> (temp.estimatedTime() < currentBest.estimatedTime())
                || ((temp.estimatedTime() == currentBest.estimatedTime()) &&
                (temp.price() < currentBest.price()));

        };
    }

    @Override
    public MapEntity[][] getLayout() {
        return mapLayout;
    }
}
