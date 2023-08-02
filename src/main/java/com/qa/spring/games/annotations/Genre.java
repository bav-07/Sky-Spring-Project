package com.qa.spring.games.annotations;

import com.qa.spring.games.validation.GenreValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.List;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = GenreValidator.class)
public @interface Genre {

    String message() default "{Genre.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };

}
