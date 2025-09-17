package com.test;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

class MainTest {

    private LogicKey logicKey = new LogicKey();
    private SolveKeyTest solveKeyTest = new SolveKeyTest();

    // Test pour la création de 3 nombres aleatoire
    @Test
    void testCheckIfRandomWorks() {
        System.out.println(logicKey.randomKeytoFind());
    }

    // Test pour résoudre la clé par l'utilisateur
    @Test
    void testCheckIfKeyCorrect() {

        List<Integer> numberUser = new ArrayList<>();
        numberUser.add(1);
        numberUser.add(2);
        numberUser.add(3);

        System.out.println("Clé utilisateur : " + numberUser);

        List<Integer> keyToFind = new ArrayList<>();
        keyToFind.addAll(logicKey.randomKeytoFind());
        System.out.println("Clé à trouvé : " + keyToFind);

        System.out.println(solveKeyTest.checkIfKeyCorrect(numberUser, keyToFind));
        solveKeyTest.hint(solveKeyTest.checkIfKeyCorrect(numberUser, keyToFind));

        assertEquals(false, solveKeyTest.verifyIfItsOk(solveKeyTest.checkIfKeyCorrect(numberUser, keyToFind))); // s'arrete uniquement si la clé est trouvé
    }

}
