# JUniqueValidator

## 1. Description

It's simple solution to validate *Unique* Constraint while using Hibernate framework.  

Just add *Unique* annotation to your filed in code and validate it.  

## 2. Language

All is in Java.

## 2. Features

1. #### Simple to use

2. #### Extends Hibernate Validation Framework

## 3. Requirements

#### 1. Hibernate Validator (API Java Validation JSR 380) 

While using Maven - add to your pom.xml:

```xml
<dependency>
    <groupId>org.hibernate.validator</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>6.0.17.Final</version>
</dependency>

```



## 4. Installation

1. Download JUniqueValidator package - 

   [JUniqueValidator]: https://github.com/pietia28/juniquevalidator

2. Add JUniqueValidator package to your Java project

## 5. Sample use

Below mentioned sample code is based on IoC technology. 

#### 1. Validate *Unique* value while creating record in DB.

1. User create:

```java
public class CustomerCreateDTO {
   ...

    @Unique(fieldName = "idax", handler = CustomerService.class, message = MessageContent.VALID_UNIQUE)
    String idax;
}
```

2. Unique validation handler:

```java
@RequiredArgsConstructor
@Service
public class CustomerService implements UniqueHandler {
    private final CustomerRepository customerRepository;
    ...
        
    @Override
    public boolean isUnique(String fieldName, String value) {
        return !customerRepository.exists(createExample(fieldName, value));
    }

    private Example<Customer> createExample(String fieldName, String value) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.ignoreCase());

        var customer = new Customer();
        try {
            var field = customer.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(customer, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return Example.of(customer, matcher);
    }
}
```



#### 2. Validate *Unique* while updating record in DB.

1. User update class:

   ```java
   public class CustomerUpdateDTO {
       ...
   
       @Unique(fieldName = "idax", handler = CustomerService.class, operation = Operation.UPDATE, message = MessageContent.VALID_UNIQUE)
       String idax;
   }
   ```

   

2. Unique validation handler class:

```java
@RequiredArgsConstructor
@Service
public class CustomerService implements UniqueHandler {
    private final CustomerRepository customerRepository;
    ...
        
   
    @Override
    public boolean isUnique(String fieldName, String value, Long id) {
        var example = createExample(fieldName, value);
        if(customerRepository.exists(example)) {
            var customer = customerRepository.findOne(example);
            return customer.get().getId().equals(id);
        }
        return true;
    }

    private Example<Customer> createExample(String fieldName, String value) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher(fieldName, ExampleMatcher.GenericPropertyMatchers.ignoreCase());

        var customer = new Customer();
        try {
            var field = customer.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(customer, value);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return Example.of(customer, matcher);
    }
}
```



## 6. Annotation options

```java
@Unique([fieldName], [handler], [message], [operation])
```

- fieldName (String) - name of field to check
- handler (Object) - -name of class where*Unique* value checking will be implemented 
- message (String) - error message
- operation (enum):
  - Create  (default) - in case of creating new entry in DB
  - Update - in case of updating existing entry

## 6. About

Version - 1.0.0

Author e-mail: pege28@wp.pl

## 7. License 

MIT - just download and enjoy.

You can modify, copy and use this code without any limitation. 



