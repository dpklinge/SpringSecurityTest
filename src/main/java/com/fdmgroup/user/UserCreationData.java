package com.fdmgroup.user;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fdmgroup.user.validation.ValidPassword;
import com.fdmgroup.user.validation.ValidUsername;

/**
 * Temporary data holder to be filled in by the Spring form in registration.html.
 * {@link ValidPassword} and {@link ValidUsername}, when used in conjunction with Valid annotation in LoginRegistrationController's
 * registerUserAccount method, will cause Spring to automatically use the UsernameValidator and PasswordValidator classes to validate
 * the username and password. ValidPassword is applied to the class rather than a field because it needs to access two fields (password and confirmPassword)
 * instead of one.
 * @author FDM Group
 *
 */
@ValidPassword
public class UserCreationData {
	@ValidUsername
	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String firstName;
	@NotNull
	@NotEmpty
	private String lastName;
	private Set<UserRoles> authorities;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@NotEmpty
	private String passwordConfirm;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Set<UserRoles> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Set<UserRoles> authorities) {
		this.authorities = authorities;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	
	
	
	

}
