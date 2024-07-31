package io.e4i2.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {
    private ValidEnum annotation;
    
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }
    
    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext constraintValidatorContext) {
        Object[] enumConstants = this.annotation.enumClass().getEnumConstants();
        if (enumConstants != null) {
            for (Object enumConstant : enumConstants) {
                if (enumConstant == value) {
                    return true;
                }
            }
        }
        return false;
    }
}
