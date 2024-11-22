package bg.sofia.uni.fmi.mjt.glovo.exception;

public class MapInitializationException extends RuntimeException {
    public MapInitializationException(String message) {
        super(message);
    }

    public MapInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
