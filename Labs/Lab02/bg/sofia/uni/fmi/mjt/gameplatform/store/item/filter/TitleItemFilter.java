package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.util.Locale;

public class TitleItemFilter implements  ItemFilter{
    private String title;
    private boolean caseSensitive;

    public TitleItemFilter(String title, boolean caseSensitive){
        setTitle(title);
        setCaseSensitive(caseSensitive);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(StoreItem item) {
        String titleItem = item.getTitle();
        if(!caseSensitive) {
            titleItem = titleItem.toLowerCase();
            return titleItem.contains(title.toLowerCase());
        }
        return titleItem.contains(title);
    }
}
