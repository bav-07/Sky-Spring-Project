package com.qa.spring.games.validation;

import com.qa.spring.games.annotations.Genre;
import com.qa.spring.games.domain.Game;
import com.qa.spring.games.enums.GameGenre;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenreValidator implements ConstraintValidator<Genre, String> {
    @Override
    public void initialize(Genre constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        for (int i = 0; i < GameGenre.values().length; i++) {
            if (GameGenre.values()[i].name().compareToIgnoreCase(value.trim()) == 0) return true;
        }
        return false;
    }

}
