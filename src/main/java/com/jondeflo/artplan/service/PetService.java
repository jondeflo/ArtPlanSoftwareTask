package com.jondeflo.artplan.service;

import com.jondeflo.artplan.model.Kind;
import com.jondeflo.artplan.model.Pet;
import com.jondeflo.artplan.model.User;
import com.jondeflo.artplan.repository.KindRepository;
import com.jondeflo.artplan.repository.PetRepository;
import com.jondeflo.artplan.repository.UserRepository;
import com.jondeflo.artplan.validation.PetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    KindRepository kindRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PetValidator petValidator;
    @Autowired
    private ResponseHandler responseHandler;

    public ResponseEntity<Object> findPetById(Object petId) {

        Long tmp = petValidator.validateId(petId);
        if (tmp == null)
            return responseHandler.getErrorResponse(1001,"Wrong pet parameters", HttpStatus.BAD_REQUEST);

        Pet pet = petRepository.findFirstById(tmp);

        if (pet != null)
            return responseHandler.getPetInfo(pet, HttpStatus.OK);
        return responseHandler.getResponse("Pet not found", HttpStatus.OK);
    }


    public ResponseEntity<Object> addPet(Map<String, String> petData) {
        if (!petValidator.validateNewPet(petData))
            return responseHandler.getErrorResponse(1001,"Wrong pet parameters", HttpStatus.BAD_REQUEST);

        if (isPetAlreadyExists(petData.get("name")))
            return responseHandler.getErrorResponse(1002,"Pet " + petData.get("name") + " already exists", HttpStatus.BAD_REQUEST);

        String name = petData.get("name");
        Kind kind = kindRepository.findFirstByKind(petData.get("kind"));
        Date birthdate = petValidator.validateDate(petData.get("birthdate"));
        boolean sex = petData.get("sex").equalsIgnoreCase("male");
        User owner = userService.getCurrentUser();

        Pet pet = new Pet(name, kind, birthdate, sex, owner);
        petRepository.save(pet);

        return responseHandler.getResponse("Pet " + name + " was successfully added", HttpStatus.OK);
    }

    public ResponseEntity<Object> editPet(Map<String, String> petData) {
        if (!petValidator.validateEditionData(petData))
            return responseHandler.getErrorResponse(1001,"Wrong pet parameters", HttpStatus.BAD_REQUEST);
        if (!isPetAlreadyExists(petData.get("name")))
            return responseHandler.getErrorResponse(1003, "Pet " + petData.get("name") + " is not exists", HttpStatus.BAD_REQUEST);

        Pet pet = petRepository.findFirstByName(petData.get("name"));
        User user = userService.getCurrentUser();

        if (!pet.getOwner().getId().equals(user.getId()))
            return responseHandler.getErrorResponse(1004, "Pet information can only be edited by the owner", HttpStatus.BAD_REQUEST);

        if (petData.containsKey("kind"))
            pet.setKind(kindRepository.findFirstByKind(petData.get("kind")));
        if (petData.containsKey("birthday"))
            pet.setBirthdate(petValidator.validateDate(petData.get("birthday")));
        if (petData.containsKey("sex"))
            pet.setSex(petData.get("sex").equalsIgnoreCase("male"));
        if (petData.containsKey("newname")) {
            if (isPetAlreadyExists(petData.get("newname")))
                return responseHandler.getErrorResponse(1002,"Pet " + petData.get("newname") + " already exists", HttpStatus.BAD_REQUEST);
            else
                pet.setName(petData.get("newname"));
        }
        if (petData.containsKey("newowner")) {
            if (userRepository.findFirstByName(petData.get("newowner")) == null)
                return responseHandler.getErrorResponse(1005, "Pet owner " + petData.get("newowner") + " does not exist", HttpStatus.BAD_REQUEST);
            else
                pet.setOwner(userRepository.findFirstByName(petData.get("newowner")));
        }

        petRepository.save(pet);
        return responseHandler.getResponse("Pet " + pet.getName() + " information was updated", HttpStatus.OK);
    }

    public ResponseEntity<Object> deletePet(Map<String, String> userData) {

        Pet pet = petRepository.findFirstByName(userData.get("name"));
        User user = userService.getCurrentUser();
        if (!petValidator.validateDeletionData(userData) || pet == null)
            return responseHandler.getErrorResponse(1001,"Wrong pet parameters", HttpStatus.BAD_REQUEST);

        if (!pet.getOwner().getId().equals(user.getId()))
            return responseHandler.getErrorResponse(1006, "Pet information can only be deleted by the owner", HttpStatus.BAD_REQUEST);

        petRepository.delete(pet);
        return responseHandler.getResponse("Pet " + pet.getName() + " was successfully removed", HttpStatus.OK);

    }

    public ResponseEntity<Object>  listAllPets() {
        List<Pet> list = petRepository.findAllByOwnerId(userService.getCurrentUser().getId());
        return responseHandler.getPetList(list, HttpStatus.OK);
    }

    private boolean isPetAlreadyExists(String name) {
        Pet pet = petRepository.findFirstByName(name);
        if (pet == null)
            return false;
        return true;
    }

}
