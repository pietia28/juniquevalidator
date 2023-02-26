package juniquevalidator;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator implements ConstraintValidator<Unique, String> {
    private String fieldName;
    private Class<?> handler;
    private Operation operation;

    private String[] ignorePaths;
    private final ApplicationContext applicationContext;

    public UniqueValidator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
        this.handler = constraintAnnotation.handler();
        this.operation = constraintAnnotation.operation();
        this.ignorePaths = constraintAnnotation.ignorePaths();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequest();
        var uniqueHandler = (UniqueHandler) applicationContext.getAutowireCapableBeanFactory().createBean(handler);

        if(operation == Operation.UPDATE) {
            String[] tmps = uri.toUriString().split("/"); //TODO zastanowic sie nad tym - sprobowac zrobic to bardziej generycznie
            Long id = Long.parseLong(tmps[tmps.length - 1]);
            return uniqueHandler.isUnique(fieldName, value, id, ignorePaths);
        }

        return uniqueHandler.isUnique(fieldName, value, ignorePaths);
    }
}
