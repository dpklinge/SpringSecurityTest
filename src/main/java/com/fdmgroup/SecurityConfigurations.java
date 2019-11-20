package com.fdmgroup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fdmgroup.user.CustomUserDetailsService;

/**
 * This is where the configuration details go for most of the Spring Security
 * concerns. The overridden configure(HttpSecurity http) will do most of the
 * standard functionality.
 * 
 * @author FDM Group
 *
 */
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	//This bean lets us override the standard user details service, allowing us to use our own
	//implementation of the UserDetails interface that Spring Security uses, along with hooking it 
	//into our own database access system.
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	//This bean specifies the methodology that will be used to encrypt the passwords inputted by users.
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//This bean tells Spring to use the password encoder and user service details implementations we established above 
	//when it authenticates user details against our database.
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
				//Lists URLs with which security permissions they are allowed.
				.authorizeRequests().antMatchers("/secure").hasAnyAuthority("ADMIN")
				.and()
				.authorizeRequests()
				.antMatchers("/", "/login", "/resources/**", "/register").permitAll()
				.and()
				.authorizeRequests().antMatchers("/**").hasAnyAuthority("ADMIN", "USER")
				.and()
				//Lists details about what page and information to use to login. If no information is provided,
				//Spring will supply a default login page.
				.formLogin().loginPage("/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.permitAll()
						.loginProcessingUrl("/doLogin")
						.successForwardUrl("/postLogin")
						.failureUrl("/loginFailed")
				.and()
				.logout().logoutUrl("/logout").permitAll()
				.and()
				//Page user is sent to when they have insufficient authorities to access
				.exceptionHandling().accessDeniedPage("/accessDenied")
				.and()
				//Page user is sent to when they have no authorities. Takes an argument of AuthentificationEntryPoint,
				//which can be represented with a lambda function.
				.exceptionHandling().authenticationEntryPoint((req, resp, exception) -> {
					req.getRequestDispatcher("/notLoggedIn").forward(req, resp);
				});
				//.and()
				//.csrf().disable();
				//CSRF stands for Cross Site Request Forgery - essentially, bad actors will fake a request from
				//another site for malicious purposes. For example, submitting a request to transfer money to 
				//their bank account when you load their page.
				//Many tutorials disable csrf to reduce complexity, but the official Spring documentation is 
				//to leave it enabled (the default) for any application where URLs are accessible to users-
				//effectively any standard web application.
				
	}

}
