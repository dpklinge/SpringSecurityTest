package com.fdmgroup.webcontrollers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserHomeController {
	
	@GetMapping("/userHome")
	@PostMapping("/userHome")
	public String userHome(HttpSession session) {
		return "userHome";
	}

}
