package bg.sofia.uni.fmi.mjt.glovo.controlcenter.utility;

public class ValidationUtils {

    public static <T> void validateObjectIsNotNull(T obj, String errorMssg) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMssg);
        }
    }

}
