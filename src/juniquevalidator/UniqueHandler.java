package juniquevalidator;

public interface UniqueHandler {
    boolean isUnique(String fieldName, String value, String[] ignorePaths);

    boolean isUnique(String fieldName, String value, Long id, String[] ignorePaths);
}
