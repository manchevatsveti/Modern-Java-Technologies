package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.*;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.*;

import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        DeferredEventSubscriber<Event<Payload<?>>> subscriber = new DeferredEventSubscriber<>();

        Instant now = Instant.now();

        // Adding events with different priorities and timestamps
        subscriber.onEvent(new CustomEvent(2, "Source1", now.plusSeconds(10), null));
        subscriber.onEvent(new CustomEvent(1, "Source2", now.plusSeconds(5), null));
        subscriber.onEvent(new CustomEvent(2, "Source3", now.plusSeconds(3), null));

        // Iterating over sorted events
        for (Event<?> event : subscriber) {
            System.out.println(event);
        }

    }
}
