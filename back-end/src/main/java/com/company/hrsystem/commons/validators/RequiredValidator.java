package com.company.hrsystem.commons.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.ObjectUtils;

import com.company.hrsystem.commons.validators.constraints.Required;

public class RequiredValidator implements ConstraintValidator<Required, CharSequence> {

	@Override
	public void initialize(Required constraintAnnotation) {
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		return ObjectUtils.isEmpty(value) ? false : true;
	}

}
