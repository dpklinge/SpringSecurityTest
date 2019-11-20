package com.fdmgroup.user;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	private static final long serialVersionUID = 5031693369982428861L;
	@Id
	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String firstName;
	@NotNull
	@NotEmpty
	private String lastName;
	@ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	private Set<UserRoles> authorities;
	@NotNull
	@NotEmpty
	private String password;

	/**
	 * All users have the role USER; other roles are assigned manually.
	 */
	public User() {
		authorities = new HashSet<>();
		authorities.add(UserRoles.USER);
	}

	/**
	 * @param userData
	 *            Uses the UserCreationData class produced by the Spring Form as a
	 *            temporary storage.
	 * @param passwordEncoder
	 *            Used to hash and salt the plain text password for storage.
	 */
	public User(UserCreationData userData, BCryptPasswordEncoder passwordEncoder) {
		this();
		username = userData.getUsername();
		firstName = userData.getFirstName();
		lastName = userData.getLastName();
		password = passwordEncoder.encode(userData.getPassword());
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", authorities="
				+ authorities + ", password=" + password + "]";
	}

	//Converts enum UserRoles into GrantedAuthorities for Spring
	public Set<GrantedAuthority> getAuthorities() {
		return authorities.stream().map(userRole -> new SimpleGrantedAuthority(userRole.toString()))
				.collect(Collectors.toSet());
	}

	public void setAuthorities(Set<UserRoles> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
