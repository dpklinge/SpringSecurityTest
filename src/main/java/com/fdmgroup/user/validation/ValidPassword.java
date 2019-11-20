package com.fdmgroup.user.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * An annotation indicating that the password in the given class should be checked for validity using the specified class-
 * {@link PasswordValidtor}). The specified implementation currently will only allow this to be used on a {@link UserCreationData} class.
 * @author FDM Group 
 *
 */
@Target({TYPE, ANNOTATION_TYPE}) 
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
	String message() default "Passwords didn't match";
    Class<?>[] groups() default {}; 
    Class<? extends Payload>[] payload() default {};

}
