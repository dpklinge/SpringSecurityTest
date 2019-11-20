package com.fdmgroup.user.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/**
 * An annotation indicating that the username in the given class should be checked for validity using the specified class-
 * {@link UsernameValidtor}). The specified implementation currently will only allow this to be used on a String field of a {@link UserCreationData} class.
 * @author FDM Group 
 *
 */
@Target({FIELD}) 
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameValidator.class)
@Documented
public @interface ValidUsername {
	String message() default "Username already taken.";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};
}
