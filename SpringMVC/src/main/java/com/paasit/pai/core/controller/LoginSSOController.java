package com.paasit.pai.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginSSOController {

	@GetMapping("/login")
	public String loginSso() {
		return String.valueOf(System.currentTimeMillis());
	}

}
