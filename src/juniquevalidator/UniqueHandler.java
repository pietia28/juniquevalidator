package juniquevalidator;

public interface UniqueHandler {
    boolean isUnique(String fieldName, String value);

    boolean isUnique(String fieldName, String value, Long id);
}
