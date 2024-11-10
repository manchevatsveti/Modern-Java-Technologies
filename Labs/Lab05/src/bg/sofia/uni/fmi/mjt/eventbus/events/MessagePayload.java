package bg.sofia.uni.fmi.mjt.eventbus.events;

public class MessagePayload implements Payload<String> {
    private final String message;
    private final int size;

    public MessagePayload(String message) {
        this.message = message;
        this.size = message.length();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getPayload() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
