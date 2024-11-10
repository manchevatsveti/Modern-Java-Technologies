package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class CustomEvent implements Event<Payload<?>> {
    private final int priority;
    private final String source;
    private final Instant timestamp;
    private final Payload<?> payload;

    public CustomEvent(int priority, String source, Instant timestamp, Payload<?> payload) {
        this.priority = priority;
        this.source = source;
        this.timestamp = timestamp;
        this.payload = payload;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public Payload<?> getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
            "priority=" + priority +
            ", source='" + source + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
