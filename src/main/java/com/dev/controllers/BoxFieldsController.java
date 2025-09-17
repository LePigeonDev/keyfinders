package com.dev.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * Contrôleur utilitaire pour chaîner des TextField façon OTP/PIN :
 * - Limite à maxNumberPerBox caractères par champ (1 par défaut).
 * - Autorise uniquement des chiffres (0-9).
 * - Si l'utilisateur tape/colle plus que la limite, l'excédent est envoyé aux champs suivants.
 * - Backspace dans un champ vide : retour au champ précédent.
 */
public class BoxFieldsController {

    private final int maxNumberPerBox;
    private List<TextField> boxList; // stocker les champs passés

    public BoxFieldsController() {
        this(1);
    }

    public BoxFieldsController(int maxNumberPerBox) {
        if (maxNumberPerBox < 1) throw new IllegalArgumentException("maxNumberPerBox must be >= 1");
        this.maxNumberPerBox = maxNumberPerBox;
    }

    public void setupFields(TextField... fields) {
        this.boxList = Arrays.asList(fields);

        for (int i = 0; i < boxList.size(); i++) {
            final int idx = i;
            final TextField tf = boxList.get(i);

            tf.focusedProperty().addListener((o, oldF, newF) -> {
                if (newF) tf.selectAll();
            });

            tf.textProperty().addListener((obs, old, newVal) -> {
                if (newVal == null) return;

                // ✅ On supprime tout ce qui n'est pas un chiffre
                String digitsOnly = newVal.replaceAll("[^0-9]", "");

                if (!digitsOnly.equals(newVal)) {
                    tf.setText(digitsOnly);
                    return;
                }

                if (digitsOnly.length() > maxNumberPerBox) {
                    String keep = digitsOnly.substring(0, maxNumberPerBox);
                    String rest = digitsOnly.substring(maxNumberPerBox);

                    if (!keep.equals(tf.getText())) {
                        tf.setText(keep);
                    }

                    if (!rest.isEmpty() && idx + 1 < boxList.size()) {
                        Platform.runLater(() -> distributeToNext(boxList, idx + 1, rest));
                    }
                } else if (digitsOnly.length() == maxNumberPerBox && idx + 1 < boxList.size()) {
                    Platform.runLater(() -> boxList.get(idx + 1).requestFocus());
                }
            });

            tf.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.BACK_SPACE && tf.getText().isEmpty() && idx > 0) {
                    TextField prev = boxList.get(idx - 1);
                    prev.requestFocus();
                    prev.positionCaret(prev.getText() == null ? 0 : prev.getText().length());
                    e.consume();
                }
            });
        }
    }

    private void distributeToNext(List<TextField> fields, int startIndex, String text) {
        int idx = startIndex;
        int pos = 0;

        while (idx < fields.size() && pos < text.length()) {
            TextField tf = fields.get(idx);
            String existing = tf.getText() == null ? "" : tf.getText();

            int capacity = Math.max(0, maxNumberPerBox - existing.length());
            if (capacity > 0) {
                int end = Math.min(pos + capacity, text.length());
                String chunk = text.substring(pos, end);
                tf.setText(existing + chunk);
                pos = end;
            }

            if (tf.getText().length() >= maxNumberPerBox) {
                idx++;
            } else {
                break;
            }
        }

        int focusIdx = Math.min(idx, fields.size() - 1);
        TextField focusField = fields.get(focusIdx);
        focusField.requestFocus();
        focusField.positionCaret(focusField.getText() == null ? 0 : focusField.getText().length());
    }

    /**
     * Récupère la liste des valeurs entières contenues dans les champs
     * puis efface les champs. Retourne une liste vide si un champ n'est pas rempli.
     */
    public List<Integer> getListBoxFields() {
        if (boxList == null || boxList.isEmpty()) return new ArrayList<>();

        List<Integer> result = new ArrayList<>();
        for (TextField tf : boxList) {
            String txt = tf.getText();
            if (txt == null || txt.isEmpty()) {
                return new ArrayList<>(); // si un champ est vide, retourne une liste vide
            }
            result.add(Integer.parseInt(txt));
        }

        // Efface tous les champs après lecture
        for (TextField tf : boxList) {
            tf.clear();
        }

        return result;
    }
}
