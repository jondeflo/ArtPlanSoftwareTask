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
public class PetController {

	@Autowired
	private PetService petService;

	@GetMapping(value = "/pet/info/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getPetById(@PathVariable("petId") Object petId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("current user is " + currentPrincipalName);

		return petService.findPetById(petId);
	}

	@GetMapping(value = "/pet/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllPets() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println("current user is " + currentPrincipalName);

		return petService.listAllPets();
	}

	// name, kind, birthday, sex
	@PostMapping(value = "/pet/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addPet(@RequestBody Map<String, String> userData) {

		return petService.addPet(userData);
	}

	// name || newName || kind || birthday || sex || newowner
	@PostMapping(value = "/pet/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editPet(@RequestBody Map<String, String> userData) {

		return petService.editPet(userData);
	}

	// name
	@PostMapping(value = "/pet/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deletePet(@RequestBody Map<String, String> userData) {

		return petService.deletePet(userData);
	}

}
