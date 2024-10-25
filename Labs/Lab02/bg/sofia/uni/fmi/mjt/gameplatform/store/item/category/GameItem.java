package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class GameItem implements StoreItem{
    private String title;
    private BigDecimal price;
    private LocalDateTime releaseDate;

    private double sumOfRatings;
    private int numberOfRatings;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public double getRating() {
        return sumOfRatings/(double)numberOfRatings;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        if(price.compareTo(BigDecimal.ZERO) < 0){
            this.price = BigDecimal.ZERO;
        }
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating) {
        sumOfRatings += rating;
        numberOfRatings++;
    }
}
