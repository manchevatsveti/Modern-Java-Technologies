package bg.sofia.uni.fmi.mjt.glovo.exception;

public class IllegalMapEntityException extends RuntimeException {
    public IllegalMapEntityException(String message) {
        super(message);
    }

    public IllegalMapEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
