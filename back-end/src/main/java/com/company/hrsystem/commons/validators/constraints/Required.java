package com.company.hrsystem.commons.validators.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.company.hrsystem.commons.validators.RequiredValidator;

@Documented
@Constraint(validatedBy = RequiredValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {

	String message() default "Required";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
