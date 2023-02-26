package juniquevalidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = UniqueValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String fieldName();
    Class<?> handler();;
    String message() default "{unique.error.message}";
    Operation operation() default Operation.CREATE;
    String[] ignorePaths() default {};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
