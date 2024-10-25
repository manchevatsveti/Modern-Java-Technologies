# GameStore 🎮 🛒

Black Friday is approaching, and your task is to create an online platform—a game store in the style of Steam or Epic Games.

The store should allow users to search by filters, apply promotional codes (drastically reducing prices during the "Deal of the Century"), and rate games and products.

---

## StoreItem

The items offered will include games, downloadable content (DLC), and various special bundles for dedicated gamers. All items implement the `StoreItem` interface, as shown below:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface StoreItem {

    /**
     * Returns the title of the store item.
     *
     * @return the title of the store item
     */
    String getTitle();

    /**
     * Returns the price of the store item.
     *
     * @return the price of the store item
     */
    BigDecimal getPrice();

    /**
     * Returns the average rating of the store item.
     *
     * @return the average rating of the store item
     */
    double getRating();

    /**
     * Returns the release date of the store item.
     *
     * @return the release date of the store item
     */
    LocalDateTime getReleaseDate();

    /**
     * Sets the title of the store item.
     *
     * @param title the title of the store item
     */
    void setTitle(String title);

    /**
     * Sets the price of the store item.
     *
     * @param price the price of the store item
     */
    void setPrice(BigDecimal price);

    /**
     * Sets the release date of the store item.
     *
     * @param releaseDate the release date of the store item
     */
    void setReleaseDate(LocalDateTime releaseDate);

    /**
     * Rates the store item.
     *
     * @param rating the rating to be given for the store item
     */
    void rate(double rating);
}
```

### Item Categories

Create the following items in the `bg.sofia.uni.fmi.mjt.gameplatform.store.item.category` package:

- `public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre)`
- `public DLC(String title, BigDecimal price, LocalDateTime releaseDate, Game game)`
- `public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games)`

---

## ItemFilter

Users can use various criteria to filter items by price, rating, or release date to find the items they are looking for. Filters are implemented using the `ItemFilter` interface:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

/**
 * A filter used to search for store items.
 */
public interface ItemFilter {

    /**
     * Checks if the given store item matches the filter.
     *
     * @param item the store item to be checked
     * @return true if the store item matches the filter, false otherwise
     */
    boolean matches(StoreItem item);
}
```

### Expected Filters

The following filters are expected in the `bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter` package:

- **Price Range Filter**: accepts two boundaries:
  - `lowerBound`: the minimum price the item must have.
  - `upperBound`: the maximum price the item must not exceed.
  
  ```java
  public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound)
  ```

- **Rating Filter**: accepts a number specifying the minimum rating the item must have to be included in the results.

  ```java
  public RatingItemFilter(double rating)
  ```

- **Release Date Filter**: accepts two boundaries:
  - `lowerBound`: the minimum release date the item must have.
  - `upperBound`: the maximum release date the item must not exceed.
  
  ```java
  public ReleaseDateItemFilter(LocalDateTime lowerBound, LocalDateTime upperBound)
  ```

- **Title Filter**: accepts the following parameters:
  - `title`: part or full name of the title being searched for.
  - `caseSensitive`: flag indicating if the title search should be case-sensitive.

  ```java
  public TitleItemFilter(String title, boolean caseSensitive)
  ```

---

## StoreAPI

In the `bg.sofia.uni.fmi.mjt.gameplatform.store` package, create the `GameStore` class with a public constructor:

```java
public GameStore(StoreItem[] availableItems)
```

It should implement the following interface:

```java
package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

public interface StoreAPI {

    /**
     * Finds all store items that match the given filters.
     *
     * @param itemFilters the filters to be applied
     * @return an array of store items that match all filters or an empty array if no such items are found
     */
    StoreItem[] findItemByFilters(ItemFilter[] itemFilters);

    /**
     * Applies a promo code discount to all store items.
     * If the promo code is not valid, no discount is applied.
     *
     * @param promoCode the promo code to be applied
     */
    void applyDiscount(String promoCode);

    /**
     * Rates a store item.
     *
     * @param item the item to be rated
     * @param rating the rating to be given in the range [1, 5]
     * @return true if the item is successfully rated, false otherwise
     */
    boolean rateItem(StoreItem item, int rating);
}
```

### Hints

- **Working with Dates**: Use the `java.time` API, focusing on the `LocalDateTime` class and its methods.
- **Prices**: Represent prices with `java.math.BigDecimal` (check its documentation).

### Promo Codes

Valid promo codes:

- `VAN40` - 40% discount on all items in the store.
- `100YO` - legendary promo code with a 100% discount on store items (for friends only).

---

## Price Format

Prices should follow the format `X.XX` with exactly two decimal places.

---

## Package Structure

Ensure the package names for all classes, interfaces, and constructors match the specifications below; otherwise, the solution may not be automatically tested.

```plaintext
src
└─ bg.sofia.uni.fmi.mjt.gameplatform
    └──── store
           ├──── item
           │      ├──── category
           │      │      ├─ Game.java
           │      │      ├─ DLC.java
           │      │      └─ GameBundle.java
           │      │
           │      ├──── filter
           │      │      ├─ PriceItemFilter.java
           │      │      ├─ RatingItemFilter.java
           │      │      ├─ ReleaseDateItemFilter.java
           │      │      ├─ TitleItemFilter.java
           │      │      └─ ItemFilter.java
           │      │
           │      └──── StoreItem.java
           │
           ├──── GameStore.java
           └──── StoreAPI.java
```

> You may add your own classes and interfaces to the respective packages if necessary.
