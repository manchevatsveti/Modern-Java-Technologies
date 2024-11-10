package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class MessageEvent implements Event<MessagePayload> {
    private final Instant timestamp;
    private final int priority;
    private final String source;
    private final MessagePayload payload;

    public MessageEvent(int priority, String source, MessagePayload payload) {
        this.timestamp = Instant.now();
        this.priority = priority;
        this.source = source;
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
    public MessagePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
            "timestamp=" + timestamp +
            ", priority=" + priority +
            ", source='" + source + '\'' +
            ", payload=" + payload +
            '}';
    }
}
