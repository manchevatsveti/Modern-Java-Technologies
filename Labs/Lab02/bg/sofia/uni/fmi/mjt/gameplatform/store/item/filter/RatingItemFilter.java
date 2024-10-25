package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

public class RatingItemFilter implements ItemFilter{
    private double rating;

    private static final double epsilon = 0.000001d;

    public RatingItemFilter(double rating){
        setRating(rating);
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean matches(StoreItem item) {
        return (Math.abs(item.getRating() - rating) < epsilon) || (item.getRating() > rating);
    }
}
