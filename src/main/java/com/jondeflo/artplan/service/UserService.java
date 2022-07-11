package com.jondeflo.artplan.service;

import com.jondeflo.artplan.config.CustomAuthenticationProvider;
import com.jondeflo.artplan.model.User;
import com.jondeflo.artplan.repository.UserRepository;
import com.jondeflo.artplan.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.Map;


@Component
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private ResponseHandler responseHandler;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity<Object> registerUser(Map<String, String> userData) {

		if (!userValidator.validateUser(userData))
			return responseHandler.getResponse("Incorrect user data", HttpStatus.BAD_REQUEST);
		if (userRepository.findFirstByName(userData.get("name")) != null)
			return responseHandler.getResponse("User with name " + userData.get("name") + " is already registered", HttpStatus.BAD_REQUEST);

		String hashedPassword = passwordEncoder.encode(userData.get("password"));
		User user = new User(userData.get("name"), hashedPassword);

		userRepository.save(user);
		forcedLogin(user);

		return responseHandler.getResponse("User registered", HttpStatus.OK);
	}

	public ResponseEntity<Object> login(Map<String, String> userData) {

		if (!userValidator.validateUser(userData))
			return responseHandler.getResponse("Wrong login request", HttpStatus.BAD_REQUEST);
		if (isUserAuthenticated())
			return responseHandler.getResponse("You must to log out before attempt to log in", HttpStatus.OK);
		if (userRepository.findFirstByName(userData.get("name")) == null)
			return responseHandler.getResponse("User with name " + userData.get("name") + " not found", HttpStatus.OK);

		User user = userRepository.findFirstByName(userData.get("name"));
		Long diff = checkLoginConstraints(user);

		if (user.getFailedAttempts() == 10 && diff < 1)
			return responseHandler.getResponse("You've entered wrong password many times. Please wait 1 hour.", HttpStatus.UNAUTHORIZED);
		if (passwordEncoder.matches(userData.get("password"), user.getPassword())) {
			performLogin(user);
			return responseHandler.getResponse("User successfully logged in", HttpStatus.OK);
		} else {
			performReject(user);
			return responseHandler.getResponse("Incorrect password", HttpStatus.UNAUTHORIZED);
		}
	}

	private void performLogin(User user) {
		user.setFailedAttempts(0);
		user.setFirstFailTime(null);
		userRepository.save(user);
		forcedLogin(user);
	}

	private void performReject(User user) {
		if (user.getFirstFailTime() == null)
			user.setFirstFailTime(new Date());
		user.setFailedAttempts(user.getFailedAttempts() + 1);
		userRepository.save(user);
	}

	private Long checkLoginConstraints(User user) {
		Long res = 0L;
		if (user.getFirstFailTime() != null) {
			Date now = new Date();
			res = (now.getTime() - user.getFirstFailTime().getTime()) / (60 * 60 * 1000);
		}
		if (user.getFailedAttempts() == 10 && res > 0) {
			user.setFailedAttempts(0);
			user.setFirstFailTime(null);
			userRepository.save(user);
		}
		return res;
	}

	private boolean isUserAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken)
			return false;
		else
			return true;
	}

	public ResponseEntity<Object> checkUserName(Object name) {
		if (!userValidator.validateCheckRequest(name))
			return responseHandler.getResponse("Incorrect request parameters", HttpStatus.BAD_REQUEST);
		if (userRepository.findFirstByName(String.valueOf(name)) != null)
			return responseHandler.getResponse("User with name " + name + " is already registered", HttpStatus.OK);
		return responseHandler.getResponse("User with name " + name + " is not registered yet", HttpStatus.OK);
	}

	private void forcedLogin(User user){
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return null;
	}
}
