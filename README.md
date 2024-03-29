# JUniqueValidator

## Version 2.0.0

## 1. Description

This is simple solution to validate *Unique* Constraint while using Hibernate framework.  

Just add *Unique* annotation to your filed in code and validate it.  

## 2. Language

All is in Java.

## 3. Features

1. #### Simple to use

2. #### Extends Hibernate Validation Framework

## 4. Requirements

#### 1. Hibernate Validator (API Java Validation JSR 380) 

While using Maven - add to your pom.xml:

```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.17.Final</version>
</dependency>

```



## 5. Installation

1. Download JUniqueValidator package - 

   [JUniqueValidator](https://github.com/pietia28/juniquevalidator/releases/tag/JUniqueValidator)

2. Add JUniqueValidator package to your Java project

## 6. Sample use

Below-mentioned sample code is based on IoC technology.
Additionally, it uses the Lombok library, which is not mandatory to use JUniqueValidator.

#### 1. Validate *Unique* value while creating record (e.g. customer) in DB.

1. Customer create:

```java
public class CustomerCreateDTO {
   ...

    @Unique(fieldName = "email", handler = CustomerService.class, ignorePaths = {"isActive"},
            message = MessageContent.VALID_UNIQUE)
    String email;
}
```

2. Unique validation handler:

```java
@RequiredArgsConstructor
@Service
public class CustomerService implements UniqueHandler {
    private final CustomerRepository customerRepository;
    ...

    /**
     * fieldName - field to validate (e.g. email)
     * value - value to validate
     * ignorePaths - list of path to ignore (boolean type is commonly used in this case)
     * Customer.class - Hibernate entity class
     */
    @Override
    public boolean isUnique(String fieldName, String value, String[] ignorePaths) {
        return !customerRepository
                .exists(ExampleCreator.createExample(fieldName, value, ignorePaths, Customer.class));
    }
}
```



#### 2. Validate *Unique* while updating record (e.g. customer) in DB.

1. Customer update:

   ```java
   public class CustomerUpdateDTO {
       ...
       @Unique(fieldName = "email", handler = CustomerService.class,
            message = MessageContent.VALID_UNIQUE,
            operation = Operation.UPDATE, ignorePaths = {"isActive"})
       String email;
   }
   ```

   

2. Unique validation handler class:

```java
@RequiredArgsConstructor
@Service
public class CustomerService implements UniqueHandler {
    private final CustomerRepository customerRepository;
    ...

    /**
     * fieldName - field to validate (e.g. email)
     * value - value to validate
     * ignorePaths - list of path to ignore (boolean type is commonly used in this case)
     * id - taken from PUT request url (e.g. http://localhost:8080/customer/12 - 12 is id)
     * Customer.class - Hibernate entity class
     */
    @Override
    public boolean isUnique(String fieldName, String value, Long id, String[] ignorePaths) {
        var example = ExampleCreator.createExample(fieldName, value, ignorePaths, Customer.class);
        if (customerRepository.findById(id).isPresent()) {
            return customerRepository.findOne(example)
                    .map(r -> r.getId().equals(id))
                    .orElse(true);
        } else {
            throw new ObjectNotFoundException(MessageContent.CUSTOMER_NOT_FOUND + id);
        }
    }
}
```



## 7. Annotation options

```java
@Unique([fieldName], [handler], [message], [operation], [ignorePaths])
```

- fieldName (String) - name of field to check
- handler (Object) - -name of class where*Unique* value checking will be implemented 
- message (String) - error message
- operation (enum):
  - Create  (default) - in case of creating new entry in DB
  - Update - in case of updating existing entry
- ignorePaths (String[]) - array of paths to ignore while matching (boolean type is commonly used in this case)

## 8. About

Version - 2.0.0

Author e-mail: pege28@wp.pl

## 9. License 

MIT - just download and enjoy.

You can modify, copy and use this code without any limitation. 



