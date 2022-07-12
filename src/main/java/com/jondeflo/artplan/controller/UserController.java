package com.jondeflo.artplan.controller;

import com.jondeflo.artplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/check/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity checkUserName(@PathVariable("name") Object name) {
		return userService.checkUserName(name);
	}

	// JSON parameters: name, password
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity registration(@RequestBody Map<String, String> userData) {
		return userService.registerUser(userData);
	}

	// JSON parameters: name, password
	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity login(@RequestBody Map<String, String> userData) {
		return userService.login(userData);
	}

}
