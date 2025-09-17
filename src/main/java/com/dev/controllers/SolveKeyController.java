package com.dev.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolveKeyController {
    
    // Sert à savoir si un ou des nombres sont correct
    public List<Integer> checkIfKeyCorrect(List<Integer> userKey, List<Integer> keyToFind) {

        List<Integer> testResult = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            if (keyToFind.get(i) == userKey.get(i)) {
                testResult.add(1);
                continue;
            } if (keyToFind.contains(userKey.get(i))) {
                testResult.add(0);
            } else {
                testResult.add(-1);
            }
        }

        return testResult;
    }

    // Phrase qui donne l'avancé de la résolution en fonction du résultat de "SolveKeyTest.checkIfKeyCorrect"
    public List<String> hint(List<Integer> testResult){

        List<String> hintList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            int result = testResult.get(i);

            switch (result) {
                case -1:
                    hintList.add("❌");
                    break;
                case 0:
                    hintList.add("〰️");
                    break;
                case 1:
                    hintList.add("✅");
                    break;
                default:
                    System.err.println("[Error] : chiffre inconnu");
                    break;
            }
        }
        return hintList;
    }

    // Verifie si la clé correspond à la clé recherché 
    public boolean verifyIfItsOk(List<Integer> testResult) {
        List<Integer> resultIsTrue = new ArrayList<>(Arrays.asList(1,1,1));

        return resultIsTrue.equals(testResult);
    }
    
}
