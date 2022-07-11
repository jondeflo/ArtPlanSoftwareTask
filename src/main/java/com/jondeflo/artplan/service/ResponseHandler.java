package com.jondeflo.artplan.service;

import com.jondeflo.artplan.model.Pet;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHandler {

    protected ResponseEntity<Object> getResponse(String message, HttpStatus status)
    {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", status.getReasonPhrase());
        response.put("message", message);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    protected ResponseEntity<Object> getPetInfo(Pet pet, HttpStatus status)
    {
        Map<String, String> response = preparePetInfo(pet);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    protected Map<String, String> preparePetInfo (Pet pet)
    {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("id", String.valueOf(pet.getId()));
        response.put("name", pet.getName());
        response.put("kind", pet.getKind());
        response.put("date of birth", pet.getBirthdate().toString().substring(0, 10));
        response.put("sex", pet.isSex() ? "male" : "female");
        return response;
    }

    protected ResponseEntity<Object> getPetList(List<Pet> list, HttpStatus status)
    {
        List<Map<String, String>> response = new LinkedList<>();

        for(Pet p : list) {
            Map<String, String> map = preparePetInfo(p);
            response.add(map);
        }
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }
}
