package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends GameItem{
    private String genre;

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre){
        super.setTitle(title);
        super.setPrice(price);
        super.setReleaseDate(releaseDate);
        setGenre(genre);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
