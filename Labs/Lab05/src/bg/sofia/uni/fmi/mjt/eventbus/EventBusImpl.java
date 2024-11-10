package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.util.ArrayList;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class EventBusImpl implements EventBus {

    private final Map<Class<? extends Event<?>>, List<Subscriber<?>>> subscriptions = new HashMap<>();
    private final List<Event<?>> eventLogs = new ArrayList<>();

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if (eventType == null) {
            throw new IllegalArgumentException("Subscribing with a null event type");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Subscribing with a null subscriber");
        }
        List<Subscriber<?>> subscribers = subscriptions.get(eventType);

        if (subscribers == null) {
            subscribers = new ArrayList<>();
            subscriptions.put(eventType, subscribers);
        }
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if (eventType == null) {
            throw new IllegalArgumentException("Unsubscribing with a null event type");
        }
        if (subscriber == null) {
            throw new IllegalArgumentException("Unsubscribing with a null subscriber");
        }
        List<Subscriber<?>> subscribers = subscriptions.get(eventType);
        if (subscribers == null || !subscribers.remove(subscriber)) {
            throw new MissingSubscriptionException("No subscription found for the given event type and subscriber");
        }
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Publishing a null event");
        }
        eventLogs.add(event);
        List<Subscriber<?>> subscribersList = subscriptions.get(event.getClass());
        if (subscribersList != null) {
            for (Subscriber<?> subscriber : subscribersList) {
                ((Subscriber<T>) subscriber).onEvent(event);
            }
        }
    }

    @Override
    public void clear() {
        subscriptions.clear();
        eventLogs.clear();
    }

    @Override
    public Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to) {
        if (eventType == null) {
            throw new IllegalArgumentException("Getting event logs with a null event type");
        }
        if (from == null || to == null) {
            throw new IllegalArgumentException("Getting event logs with a null timestamp");
        }

        List<Event<?>> filteredEvents = new ArrayList<>();

        for (Event<?> event : eventLogs) {
            if (eventType.isInstance(event) &&
                !event.getTimestamp().isBefore(from) &&
                event.getTimestamp().isBefore(to)) {
                filteredEvents.add(event);
            }
        }
        filteredEvents.sort(Comparator.comparing(Event::getTimestamp));
        return Collections.unmodifiableList(filteredEvents);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if (eventType == null) {
            throw new IllegalArgumentException("Getting subscribers with a null event type");
        }
        return Collections.unmodifiableCollection(subscriptions.getOrDefault(eventType, Collections.emptyList()));
    }
}
