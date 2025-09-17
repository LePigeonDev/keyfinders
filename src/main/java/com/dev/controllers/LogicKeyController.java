package com.dev.controllers;

import java.util.ArrayList;
import java.util.List;

import java.security.SecureRandom;

public class LogicKeyController {
    
    SecureRandom secureRandom = new SecureRandom();

    // Génére une clé aléatoire de 3 chiffres de 0 -> 9
    public List<Integer> randomKeytoFind() {
        List<Integer> KeyList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int randomInteger = secureRandom.nextInt(10);
            KeyList.add(randomInteger);
        }
        
        return KeyList;
    }

}
