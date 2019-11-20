package com.fdmgroup.webcontrollers;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import com.fdmgroup.dataaccess.SpringUserDao;
import com.fdmgroup.user.User;
import com.fdmgroup.user.UserCreationData;

/**
 * Main web controller for login and registration functionality
 * 
 * @author FDM Group
 */
@Controller
public class LoginRegistrationController {
	private static final Logger logger = LogManager.getLogger(LoginRegistrationController.class);
	@Autowired
	private SpringUserDao dao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping(value = { "/", "/login" })
	public String login(HttpSession session, Model model) {
		return "login";
	}

	@GetMapping("/loginFailed")
	public String loginError(Model model) {
		logger.debug("Login attempt failed");
		model.addAttribute("error", "Username or password invalid");
		return "login";
	}

	@GetMapping("/logout")
	public String logout(SessionStatus session) {
		logger.trace("Logging out");
		SecurityContextHolder.getContext().setAuthentication(null);
		session.setComplete();
		return "redirect:/login";
	}

	@PostMapping("/postLogin")
	public String postLogin(Model model, HttpSession session) {
		logger.trace("Login submitted to server");
		// Read principal out of security context and validate it
		// A 'principal' in web security is any entity that can be authenticated by the
		// system. In our case, our principals are users.
		UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder
				.getContext().getAuthentication();
		validatePrinciple(authentication.getPrincipal());
		User loggedInUser = ((User) authentication.getPrincipal());

		logger.debug("Logging in user : " + loggedInUser);
		session.setAttribute("user", loggedInUser);
		return "redirect:/userHome";
	}

	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("user", new UserCreationData());
		return "register";
	}

	@PostMapping("/register")
	public String registerUserAccount(@ModelAttribute("user") @Valid UserCreationData user, BindingResult result,
			WebRequest request, Errors errors, Model model) {
		logger.trace("Register submitted to server");
		if (result.hasErrors()) {
			logger.debug("Registration contained errors: " + new ArrayList<>(errors.getAllErrors()));
			// Provides error messages over each individual field in the registration.html
			// file on failure
			for (ObjectError error : errors.getAllErrors()) {
				if (error.getCode().equals("NotEmpty")) {
					model.addAttribute(error.getCodes()[1].split("\\.")[1] + "Error", "Field is required.");
				} else {
					model.addAttribute(error.getCode(), error.getDefaultMessage());
				}
			}
			return "register";
		}

		User registered = createUserAccount(user);
		model.addAttribute("username", registered.getUsername());
		model.addAttribute("message", "Great! Let's try out your new login.");
		return "login";
	}

	@GetMapping("/notLoggedIn")
	public String notLoggedIn(Model model) {
		logger.trace("User attempted to access page while not logged in.");
		model.addAttribute("error", "Please log in to access this content.");
		return "login";
	}

	@GetMapping("/accessDenied")
	public String accessDenied(Model model) {
		logger.trace("User attempted to access page with insufficient authorities.");
		model.addAttribute("error", "You do not have adequate permissions to access this page.");
		return "login";
	}
	
	@GetMapping("/secure")
	public String securePage() {
		logger.trace("Accessing secure page.");
		return "securePage";
	}

	private User createUserAccount(UserCreationData userData) {
		User user = new User(userData, passwordEncoder);
		user = dao.save(user);
		return user;
	}

	private void validatePrinciple(Object principal) {
		if (!(principal instanceof User)) {
			throw new IllegalArgumentException("Principal can not be null!");
		}
	}

}
