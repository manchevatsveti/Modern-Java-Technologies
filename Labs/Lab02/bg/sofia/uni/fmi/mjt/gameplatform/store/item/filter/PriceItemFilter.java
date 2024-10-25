package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;

public class PriceItemFilter implements ItemFilter{
    private BigDecimal lowerBound;
    private BigDecimal upperBound;

    public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound){
        if(validBounds(lowerBound, upperBound)){
            setLowerBound(lowerBound);
            setUpperBound(upperBound);
        }
    }

    private boolean validBounds(BigDecimal lowerBound, BigDecimal upperBound){
        if(lowerBound.compareTo(BigDecimal.ZERO) >= 0 && upperBound.compareTo(BigDecimal.ZERO) >= 0){
            return lowerBound.compareTo(upperBound) < 0;
        }
        return false;
    }

    public BigDecimal getLowerBound(){
        return lowerBound;
    }

    public BigDecimal getUpperBound(){
        return upperBound;
    }

    public void setLowerBound(BigDecimal lowerBound){
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(BigDecimal upperBound){
        this.upperBound = upperBound;
    }

    @Override
    public boolean matches(StoreItem item) {
        BigDecimal price = item.getPrice();
        return price.compareTo(lowerBound) >= 0 && price.compareTo(upperBound) <= 0;
    }
}
