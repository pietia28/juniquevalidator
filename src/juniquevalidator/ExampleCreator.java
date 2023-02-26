package juniquevalidator;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.lang.reflect.InvocationTargetException;

public class ExampleCreator {

    private ExampleCreator() {}
    public static <T> Example<T> createExample(String fieldName, String value, String[] ignorePaths, Class<T> clazz) {
        T obj;
        try {
            obj = clazz.getDeclaredConstructor().newInstance();
            var field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return Example.of(obj, ignorePaths.length != 0 ? ExampleMatcher.matching()
                .withIgnorePaths(ignorePaths)
                .withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.ignoreCase())
                : ExampleMatcher.matching()
                .withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.ignoreCase()));
    }
}
