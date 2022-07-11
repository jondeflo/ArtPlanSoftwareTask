package com.jondeflo.artplan.controller;

import com.jondeflo.artplan.service.PetService;
import com.jondeflo.artplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/check/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity checkUserName(@PathVariable("name") Object name) {

		return userService.checkUserName(name);
	}

	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registration(@RequestBody Map<String, String> userData) {

		return userService.registerUser(userData);
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity login(@RequestBody Map<String, String> userData) {

		return userService.login(userData);
	}

}
