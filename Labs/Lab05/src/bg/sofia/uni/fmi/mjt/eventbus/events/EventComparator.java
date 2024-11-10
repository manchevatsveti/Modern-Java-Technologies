package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.util.Comparator;

public class EventComparator<T extends Event<?>> implements Comparator<T> {

    @Override
    public int compare(T event1, T event2) {
        int priorityComparison = Integer.compare(event2.getPriority(), event1.getPriority());

        if (priorityComparison != 0) {
            return priorityComparison;
        }

        return event2.getTimestamp().compareTo(event1.getTimestamp());
    }
}
