package com.fdmgroup.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.fdmgroup.user.UserCreationData;
/**
 * The mechanism for validating a password supplied through a registration form. Currently checks if the password matches the
 * confirm password.
 * @author FDM Group
 *
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, UserCreationData> { 
	 
   @Override
   public void initialize(ValidPassword constraintAnnotation) {       
   }
   @Override
   public boolean isValid(UserCreationData user, ConstraintValidatorContext context){ 
	   if(user.getPassword()==null||user.getPassword().isEmpty()) {
		    //This disables the default message provided in @ValidPassword and supplies a new message.
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
			           "Field is required.").addConstraintViolation();
			return false;
		}
			
       return user.getPassword().equals(user.getPasswordConfirm());    
   }    

}
