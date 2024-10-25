package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import java.time.LocalDateTime;

public class ReleaseDateItemFilter implements ItemFilter{
    private LocalDateTime lowerBound;
    private LocalDateTime upperBound;

    public ReleaseDateItemFilter(LocalDateTime lowerBound, LocalDateTime upperBound){
        if(validBounds(lowerBound, upperBound)){
            setLowerBound(lowerBound);
            setUpperBound(upperBound);
        }
    }

    private boolean validBounds(LocalDateTime lowerBound, LocalDateTime upperBound){
        return lowerBound.isBefore(upperBound);
    }

    public LocalDateTime getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(LocalDateTime lowerBound) {
        this.lowerBound = lowerBound;
    }

    public LocalDateTime getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(LocalDateTime upperBound) {
        this.upperBound = upperBound;
    }

    @Override
    public boolean matches(StoreItem item) {
        LocalDateTime date = item.getReleaseDate();
        return (date.isEqual(lowerBound) || date.isAfter(lowerBound)) && (date.isEqual(upperBound) || date.isBefore(upperBound));
    }
}
