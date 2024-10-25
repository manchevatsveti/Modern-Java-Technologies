import bg.sofia.uni.fmi.mjt.gameplatform.store.*;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.DLC;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.Game;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.GameBundle;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Създаване на игри
        Game game1 = new Game("Cyberpunk 2077", new BigDecimal("59.99"), LocalDateTime.of(2020, 12, 10, 0, 0), "Action RPG");
        Game game2 = new Game("The Witcher 3", new BigDecimal("39.99"), LocalDateTime.of(2015, 5, 19, 0, 0), "RPG");
        Game game3 = new Game("Hades", new BigDecimal("24.99"), LocalDateTime.of(2020, 9, 17, 0, 0), "Roguelike");

        // Създаване на DLC
        DLC dlc1 = new DLC("Cyberpunk 2077 - Phantom Liberty", new BigDecimal("29.99"), LocalDateTime.of(2023, 9, 26, 0, 0), game1);
        DLC dlc2 = new DLC("The Witcher 3 - Blood and Wine", new BigDecimal("19.99"), LocalDateTime.of(2016, 5, 31, 0, 0), game2);

        // Създаване на пакет от игри
        Game[] gamesBundle = {game1, game2};
        GameBundle gameBundle = new GameBundle("CD Projekt Red Bundle", new BigDecimal("79.99"), LocalDateTime.of(2021, 12, 1, 0, 0), gamesBundle);

        // Създаване на налични артикули
        StoreItem[] availableItems = {game1, game2, game3, dlc1, dlc2, gameBundle};

        // Инициализиране на магазина
        GameStore gameStore = new GameStore(availableItems);

       // Приложение на промоционален код
        System.out.println("Applying VAN40 promo code...");

        gameStore.applyDiscount("van40");

        gameStore.applyDiscount("van40");
        for(StoreItem item : gameStore.getAvailableItems()){
            System.out.println(item.getTitle() + " " + item.getPrice());
        }
 /*
        // Рейтинг на артикул
        boolean ratingSuccess = gameStore.rateItem(game1, 5);
        boolean ratingSuccess2 = gameStore.rateItem(game1, 1);
        boolean ratingSuccess3 = gameStore.rateItem(game2, 3);
        boolean ratingSuccess4 = gameStore.rateItem(game1, 8);
        gameStore.rateItem(game2, 2);
        gameStore.rateItem(game3, 3);
        gameStore.rateItem(dlc1, 3);  // Note: Assuming the rating is out of 10 or adjust accordingly
        gameStore.rateItem(dlc1, 4);
        gameStore.rateItem(dlc2, 1);
        gameStore.rateItem(dlc2, 4);
        gameStore.rateItem(gameBundle, 5);
        gameStore.rateItem(gameBundle, 2);

        for(StoreItem item : gameStore.getAvailableItems()){
            System.out.println(item.getTitle() + " " + item.getRating());
        }

        // Филтриране на артикули
        System.out.println("Filtering items by price (20.00 to 60.00)...");

        ItemFilter priceFilter = new PriceItemFilter(new BigDecimal("20.00"), new BigDecimal("60.00"));
        ItemFilter ratingFilter = new RatingItemFilter(3.0);
        ItemFilter releaseDate = new ReleaseDateItemFilter(LocalDateTime.of(2019, 12, 9, 0, 0),LocalDateTime.of(2024, 9, 17, 0, 0));
        ItemFilter titleFilter = new TitleItemFilter("Cyberpunk",true);
        ItemFilter[] filters = {priceFilter, ratingFilter, releaseDate, titleFilter};

        StoreItem[] filteredItems = gameStore.findItemByFilters(filters);
        System.out.println("Filtered items by date:" + LocalDateTime.of(2020, 12, 9, 0, 0) + " - " + LocalDateTime.of(2022, 9, 17, 0, 0));
        for (StoreItem item : filteredItems) {
            System.out.println("- " + item.getTitle() + ": " + item.getReleaseDate());
        }/**/
    }
}
