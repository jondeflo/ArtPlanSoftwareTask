package com.jondeflo.artplan.controller;

import com.jondeflo.artplan.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class PetController {

	@Autowired
	private PetService petService;

	@GetMapping(value = "/pet/info/{petId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getPetById(@PathVariable("petId") Object petId) {
		return petService.findPetById(petId);
	}

	@GetMapping(value = "/pet/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllPets() {
		return petService.listAllPets();
	}

	// JSON parameters: name, kind, birthday, sex
	@PostMapping(value = "/pet/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity addPet(@RequestBody Map<String, String> userData) {
		return petService.addPet(userData);
	}

	// JSON parameters: name && ( newName || kind || birthday || sex || newowner)
	@PostMapping(value = "/pet/edit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity editPet(@RequestBody Map<String, String> userData) {
		return petService.editPet(userData);
	}

	// JSON parameters: name
	@PostMapping(value = "/pet/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deletePet(@RequestBody Map<String, String> userData) {
		return petService.deletePet(userData);
	}

}
