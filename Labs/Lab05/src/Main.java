package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.*;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.*;

public class Main {

    public static void main(String[] args) {
        // Initialize the EventBus
        EventBusImpl eventBus = new EventBusImpl();

        // Create a DeferredEventSubscriber
        DeferredEventSubscriber<MessageEvent> subscriber = new DeferredEventSubscriber<>();

        // Subscribe the subscriber to MessageEvent type
        eventBus.subscribe(MessageEvent.class, subscriber);

        // Create and publish some events
        MessageEvent event1 = new MessageEvent(1, "System", new MessagePayload("Critical system error"));
        MessageEvent event2 = new MessageEvent(2, "User", new MessagePayload("User login detected"));
        MessageEvent event3 = new MessageEvent(3, "Notification", new MessagePayload("New message received"));

        // Publish events to the event bus
        eventBus.publish(event1);
        eventBus.publish(event2);
        eventBus.publish(event3);

        // Check if the subscriber received the events
        System.out.println("Deferred events received:");
        for (MessageEvent event : subscriber) {
            System.out.println(event);
        }

        // Unsubscribe the subscriber
        try {
            eventBus.unsubscribe(MessageEvent.class, subscriber);
        } catch (MissingSubscriptionException e) {
            System.err.println("Failed to unsubscribe: " + e.getMessage());
        }

        // Clear all subscriptions and event logs
        eventBus.clear();
        System.out.println("Event bus cleared.");
    }
}
