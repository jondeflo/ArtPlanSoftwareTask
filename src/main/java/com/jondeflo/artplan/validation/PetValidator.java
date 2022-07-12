package com.jondeflo.artplan.validation;

import com.jondeflo.artplan.repository.KindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Component
public class PetValidator {

    @Autowired
    KindRepository kindRepository;

    public boolean validateEditionData(Map<String, String> petData) {

        if (petData.size() < 2)
            return false;
        if (!petData.containsKey("name"))
            return false;
        if (!petData.containsKey("newname") && !petData.containsKey("kind") && !petData.containsKey("birthdate")
                && !petData.containsKey("sex") && !petData.containsKey("newowner"))
            return false;
        for (Map.Entry<String, String> entry : petData.entrySet()) {
            if (!entry.getKey().equals("name") && !entry.getKey().equals("kind") &&!entry.getKey().equals("birthday")
                    && !entry.getKey().equals("sex") && !entry.getKey().equals("newname") && !entry.getKey().equals("newowner"))
                return false;
            if (entry.getValue().isBlank() || entry.getValue().startsWith(" "))
                return false;
        }
        if (petData.containsKey("kind") && kindRepository.findFirstByKind(petData.get("kind")) == null)
            return false;
        if (petData.containsKey("birthdate") && validateDate(petData.get("birthdate")) == null)
            return false;

        return true;
    }


    public boolean validateNewPet(Map<String, String> petData) {
        if (petData.size() != 4)
            return false;
        if (!petData.containsKey("name") || !petData.containsKey("kind")
            || !petData.containsKey("birthdate") || !petData.containsKey("sex"))
            return false;
        for (Map.Entry<String, String> entry : petData.entrySet()) {
            if (entry.getValue().isBlank() || entry.getValue().startsWith(" "))
                return false;
        }
        if (!petData.get("sex").equalsIgnoreCase("male") && !petData.get("sex").equalsIgnoreCase("female"))
            return false;
        if (kindRepository.findFirstByKind(petData.get("kind")) == null)
            return false;
        if (validateDate(petData.get("birthdate")) == null)
            return false;

        return true;
    }

    public Date validateDate(String date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date result;
        try {
            result = df.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return result;
    }

    public boolean validateDeletionData(Map<String, String> petData) {
        if (petData.size() != 1)
            return false;
        if (!petData.containsKey("name"))
            return false;
        if (petData.get("name").isBlank() || petData.get("name").startsWith(" "))
            return false;
        return true;
    }

    public Long validateId(Object petId) {
        Long res;

        try {
            res = Long.parseLong((String) petId);
        } catch (NumberFormatException e){ return null; }

        return res;
    }

}
