package application.validation;

public interface ValidationInterface {

    default boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
