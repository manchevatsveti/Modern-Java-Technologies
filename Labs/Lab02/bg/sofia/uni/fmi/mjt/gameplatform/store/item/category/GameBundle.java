package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle extends GameItem{
    private Game[] games;

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games){
        super.setTitle(title);
        super.setPrice(price);
        super.setReleaseDate(releaseDate);
        setGames(games);
    }

    public Game[] getGames(){
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }
}
