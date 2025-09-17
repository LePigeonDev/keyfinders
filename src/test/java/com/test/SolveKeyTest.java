package com.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolveKeyTest {

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
    public void hint(List<Integer> testResult){

        for(int i = 0; i < 3; i++) {
            int result = testResult.get(i);

            switch (result) {
                case -1:
                    System.out.println("Ce chiffre n'est pas dans la clé : " +  result);
                    break;
                case 0:
                    System.out.println("Ce chiffre n'est pas à la bonne place : " + result);
                    break;
                case 1:
                    System.out.println("Ce chiffre est correct : " + result);
                    break;
                default:
                    System.err.println("[Error] : chiffre inconnu");
                    break;
            }
        }
    }

    // Verifie si la clé correspond à la clé recherché 
    public boolean verifyIfItsOk(List<Integer> testResult) {
        List<Integer> resultIsTrue = new ArrayList<>(Arrays.asList(1,1,1));

        return resultIsTrue.equals(testResult);
    }
}
