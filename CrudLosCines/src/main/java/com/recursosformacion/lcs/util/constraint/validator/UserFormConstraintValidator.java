package com.recursosformacion.lcs.util.constraint.validator;

import org.springframework.beans.BeanWrapperImpl;

import com.recursosformacion.lcs.util.constraint.interfaces.UserFormConstraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserFormConstraintValidator implements ConstraintValidator<UserFormConstraint, Object>  {

	private String field;
    private String fieldMatch;

    public void initialize(UserFormConstraint constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    public boolean isValid(Object value, 
      ConstraintValidatorContext context) {

        Object fieldValue = new BeanWrapperImpl(value)
          .getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value)
          .getPropertyValue(fieldMatch);
        
        if (fieldValue != null) {
            return fieldValue.equals(fieldMatchValue);
        } else {
            return fieldMatchValue == null;
        }
    }

}
