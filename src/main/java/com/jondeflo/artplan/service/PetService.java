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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    PetValidator petValidator;

    @Autowired
    private ResponseHandler responseHandler;

    public ResponseEntity<Object> findPetById(Object petId) {

        Long tmp = petValidator.validateId(petId);

        if (tmp == null)
            return responseHandler.getResponse("Incorrect pet ID", HttpStatus.BAD_REQUEST);

        Pet pet = petRepository.findFirstById(tmp);

        if (pet != null)
            return responseHandler.getPetInfo(pet, HttpStatus.OK);

        return responseHandler.getResponse("Pet not found", HttpStatus.OK);
    }


    public ResponseEntity<Object> addPet(Map<String, String> userData)
    {
        if (!petValidator.validateNewPet(userData))
            return responseHandler.getResponse("Wrong pet parameters", HttpStatus.BAD_REQUEST);

        if (checkPetAlreadyExist(userData.get("name")))
            return responseHandler.getResponse("Pet with name " + userData.get("name") + " is exists already", HttpStatus.BAD_REQUEST);

        String name = userData.get("name");
        Kind kind = kindRepository.findFirstByKind(userData.get("kind"));
        Date birthdate = petValidator.validateDate(userData.get("birthdate"));
        boolean sex = userData.get("sex").equalsIgnoreCase("male");
        User owner = getCurrentUser();

        Pet pet = new Pet(name, kind, birthdate, sex, owner);
        petRepository.save(pet);

        return responseHandler.getResponse("Pet " + name + " was successfully added", HttpStatus.OK);
    }

    public ResponseEntity<Object> editPet(Map<String, String> petData)
    {
        if (!petValidator.validatePetEdition(petData))
            return responseHandler.getResponse("Wrong pet parameters", HttpStatus.BAD_REQUEST);
        if (!checkPetAlreadyExist(petData.get("name")))
            return responseHandler.getResponse("Pet with name " + petData.get("name") + " is not present", HttpStatus.BAD_REQUEST);

        Pet pet = petRepository.findFirstByName(petData.get("name"));
        User user = getCurrentUser();

        if (!pet.getOwner().getId().equals(user.getId()))
            return responseHandler.getResponse("Pet information may be edited by owner only", HttpStatus.BAD_REQUEST);



        if (petData.containsKey("kind"))
            pet.setKind(kindRepository.findFirstByKind(petData.get("kind")));
        if (petData.containsKey("birthday"))
            pet.setBirthdate(petValidator.validateDate(petData.get("birthday")));
        if (petData.containsKey("sex"))
            pet.setSex(petData.get("sex").equalsIgnoreCase("male"));
        if (petData.containsKey("newname")) {
            if (checkPetAlreadyExist(petData.get("newname")))
                return responseHandler.getResponse("Pet with name " + petData.containsKey("newname") + " already exists", HttpStatus.BAD_REQUEST);
            else
                pet.setName(petData.get("newname"));
        }
        if (petData.containsKey("newowner")) {
            if (userRepository.findFirstByName(petData.get("newowner")) == null)
                return responseHandler.getResponse("No owner with name  " + petData.get("newowner") + " present", HttpStatus.BAD_REQUEST);
            else
                pet.setOwner(userRepository.findFirstByName(petData.get("newowner")));
        }

        petRepository.save(pet);
        return responseHandler.getResponse("Pet " + pet.getName() + " information was updated", HttpStatus.OK);



    }

    public ResponseEntity<Object> deletePet(Map<String, String> userData)
    {

        Pet pet = petRepository.findFirstByName(userData.get("name"));
        User user = getCurrentUser();
        if (!petValidator.validateDelete(userData) || pet == null)
            return responseHandler.getResponse("Wrong pet parameters for deletion", HttpStatus.BAD_REQUEST);

        if (!pet.getOwner().getId().equals(user.getId()))
            return responseHandler.getResponse("Pet may be deleted by owner only", HttpStatus.BAD_REQUEST);

        petRepository.delete(pet);
        return responseHandler.getResponse("Pet " + pet.getName() + " was deleted", HttpStatus.OK);

    }

    private boolean checkPetAlreadyExist(String name) {
        Pet pet = petRepository.findFirstByName(name);
        if (pet == null)
            return false;
        return true;
    }

    public ResponseEntity<Object>  listAllPets()
    {
        List<Pet> list = petRepository.findAllByOwnerId(getCurrentUser().getId());
        return responseHandler.getPetList(list, HttpStatus.OK);

    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User user = userRepository.findFirstByName(currentPrincipalName);
        return user;
    }




}
