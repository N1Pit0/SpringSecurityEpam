package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.common.UserDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserService{
    public static int uniqueID = 20; // adjust value based
                                    // on maximum userID for both trainee and
                                    // trainer file before starting

    private final Map<String, Integer> usernameCounter = new HashMap<String, Integer>();

    public String generateUserName(UserDto userDTO){
        String baseUserName = userDTO.getFirstName() + "." + userDTO.getLastName();

        int count = usernameCounter.getOrDefault(baseUserName, 0);

        String newUserName = (count == 0) ? baseUserName : baseUserName + count;

        usernameCounter.put(baseUserName, count + 1);

        return newUserName;
    }

    public String generatePassword(){
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            char c = (char) (random.nextInt(33,127)); //ASCII representation of symbols for password
            password.append(c);
        }

        return password.toString();
    }
}
