# Event Bus 🔔

### Overview
In this exercise, we will leverage our knowledge of Java Generics while exploring key concepts and architectural design patterns in modern software development, such as **Event-Driven Architecture (EDA)**.

The **Event Bus** (also known as Event Broker) is an architectural design pattern commonly used in software systems to manage communication between different components of an application. This pattern allows components to publish events and subscribe to receive notifications of these events without knowing about each other, thus decoupling their interactions. Essentially, the Event Bus pattern is a more flexible and advanced version of the **Observer Design Pattern**, suitable for larger and more complex systems.

This pattern is widely utilized in modern software development as it improves the modular architecture of applications, reduces dependencies among components, and enhances application performance and responsiveness through asynchronous communication. It is commonly found in real-time event processing systems like games, chat applications, and financial platforms.

### Key Concepts
The main participants in an Event Bus are **Events** and **Subscribers**.

---

## Events
Events are notifications that something has occurred within the system. We will model events using classes that implement the following generic interface:

```java
package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public interface Event<T extends Payload<?>> {

    /**
     * @return the time when the event was created.
     */
    Instant getTimestamp();

    /**
     * @return the priority of the event. Lower number denotes higher priority.
     */
    int getPriority();

    /**
     * @return the source of the event.
     */
    String getSource();

    /**
     * @return the payload of the event.
     */
    T getPayload();
}
```

Events contain metadata like timestamp, priority, and source, and carry their main content through a generic **Payload** interface:

```java
package bg.sofia.uni.fmi.mjt.eventbus.events;

public interface Payload<T> {

    /**
     * @return the size of the payload.
     */
    int getSize();

    /**
     * @return the payload data.
     */
    T getPayload();
}
```

Different types of events can have different payload types.

---

## Subscribers
Subscribers are components that register interest in specific types of events. When a subscriber is notified of an event, it can perform actions based on the event’s content. Subscribers implement the following interface:

```java
package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public interface Subscriber<T extends Event<?>> {

    /**
     * This method is called by the EventBus when a new event for the subscribed type is published.
     *
     * @param event the event that was published.
     * @throws IllegalArgumentException if the event is null.
     */
    void onEvent(T event);
}
```

### DeferredEventSubscriber
The `DeferredEventSubscriber` is a special type of subscriber that stores all received events for later processing. You need to complete its implementation:

```java
package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import java.util.Iterator;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;

public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {

    @Override
    public void onEvent(T event) {
        throw new UnsupportedOperationException("Still not implemented");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Still not implemented");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("Still not implemented");
    }
}
```

---

## Event Bus
The core component is modeled by the `EventBusImpl` class, implementing the `EventBus` interface:

```java
package bg.sofia.uni.fmi.mjt.eventbus;

import java.time.Instant;
import java.util.Collection;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

public interface EventBus {

    <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber);

    <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException;

    <T extends Event<?>> void publish(T event);

    void clear();

    Collection<? extends Event<?>> getEventLogs(Class<? extends Event<?>> eventType, Instant from, Instant to);

    <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType);
}
```

### Implementation Notes
- Implementations of these interfaces and classes are required for local testing.
- Use the provided package structure for all types to ensure compatibility with automated tests.

### Package Structure
```
src
└── bg.sofia.uni.fmi.mjt.eventbus
    ├── events
    │      ├── Event.java 
    │      ├── Payload.java 
    ├── exception
    │      └── MissingSubscriptionException.java
    ├── subscribers
    │      ├── DeferredEventSubscriber.java
    │      ├── Subscriber.java
    ├── EventBus.java
    ├── EventBusImpl.java
```

### Hints
👉 To properly utilize `Class<T>`, remember that if `MyClass` is a class name, then `MyClass.class` is a reference of type `Class<MyClass>`. For example:

```java
Class<String> stringClassRef = String.class;
```
