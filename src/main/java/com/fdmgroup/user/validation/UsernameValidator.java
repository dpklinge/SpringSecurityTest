package com.fdmgroup.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fdmgroup.dataaccess.SpringUserDao;

/**
 * The mechanism for validating a username supplied through a registration form. Currently checks if the username already
 * exists in the current database.
 * @author FDM Group
 *
 */
@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {
	@Autowired
	private SpringUserDao dao;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		if(username==null||username.isEmpty()) {
			//This disables the default message provided in @ValidUsername and supplies a new message.
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(
			           "Field is required.").addConstraintViolation();
			return false;
		}
		return !dao.existsById(username);	
	}

}
