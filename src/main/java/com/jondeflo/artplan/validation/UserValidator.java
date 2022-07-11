package com.jondeflo.artplan.validation;

import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.Map;

@Component
public class UserValidator {

    public boolean validateUser(Map<String, String> userData)
    {
        if (userData.size() != 2)
            return false;
        if (!userData.containsKey("name") || !userData.containsKey("password"))
            return false;
        if (userData.get("name").isBlank() || userData.get("password").isBlank())
            return false;
        if (userData.get("name").startsWith(" ") || userData.get("password").startsWith(" "))
            return false;
        return true;
    }

    public boolean validateCheckRequest(Object name)
    {
       if (!String.valueOf(name).isBlank())
           return true;
       return false;
    }

}
