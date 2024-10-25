package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;

import java.math.BigDecimal;

public class GameStore implements StoreAPI{
    private StoreItem[] availableItems;

    private boolean van40Applied = false;
    private boolean stoyoApplied = false;

    private static final String van40Code = "VAN40";
    private static final String stoyoCode = "100YO";
    private static final double van40Discount = 0.4;

    public GameStore(StoreItem[] availableItems){
        setAvailableItems(availableItems);
    }

    private boolean passesAllFilters(StoreItem item, ItemFilter[] itemFilters) {
        for (ItemFilter itemFilter : itemFilters) {
            if (!itemFilter.matches(item)) {
                return false;
            }
        }
        return true;
    }

    public int getCountByFilter(ItemFilter[] itemFilters) {
        int count = 0;
        for (StoreItem availableItem : availableItems) {
            if (passesAllFilters(availableItem, itemFilters)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        int count = getCountByFilter(itemFilters);

        // If no items match the filters, return an empty array
        if (count == 0) {
            return new StoreItem[0];
        }

        StoreItem[] filteredItems = new StoreItem[count];
        int index = 0;

        for (StoreItem availableItem : availableItems) {
            if (passesAllFilters(availableItem, itemFilters)) {
                filteredItems[index++] = availableItem;
            }
        }
        return filteredItems;
    }

    @Override
    public void applyDiscount(String promoCode) {
        if (promoCode.equalsIgnoreCase(stoyoCode) && !stoyoApplied) {
            applyDiscountToAllItems(BigDecimal.ZERO);
            stoyoApplied = true;
        } else if (promoCode.equalsIgnoreCase(van40Code) && !van40Applied) {
            BigDecimal discountFactor = BigDecimal.valueOf(1.0 - van40Discount); // Calculate 60% (1 - 0.40)
            applyDiscountToAllItems(discountFactor);
            van40Applied = true;
        }
    }

    private void applyDiscountToAllItems(BigDecimal discountFactor) {
        for (StoreItem availableItem : availableItems) {
            BigDecimal currentPrice = availableItem.getPrice();
            availableItem.setPrice(currentPrice.multiply(discountFactor));
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        if(rating < 1 || rating > 5){
            return false;
        }
        item.rate(rating);
        return true;
    }

    public StoreItem[] getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(StoreItem[] availableItems) {
        this.availableItems = availableItems;
    }
}
