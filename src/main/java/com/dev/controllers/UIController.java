package com.dev.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class UIController {

    @FXML private Label winLabel;
    @FXML private TextField box1, box2, box3;
    @FXML private Button btnPlay, btnEntry, btnReplay;
    @FXML private HBox titleBar;
    @FXML private Button btnMin, btnClose;
    private double dragOffsetX, dragOffsetY;

    @FXML private TableView<HistoryRow> historyTable;
    @FXML private TableColumn<HistoryRow, String> entryUserColumn;
    @FXML private TableColumn<HistoryRow, String> hintColumn;

    private final ObservableList<HistoryRow> data = FXCollections.observableArrayList();

    private BoxFieldsController boxFieldsController = new BoxFieldsController();
    private LogicKeyController logicKeyController = new LogicKeyController();
    private SolveKeyController solveKeyController = new SolveKeyController();

    private List<Integer> scoreResult = new ArrayList<>();
    private List<Integer> randomKey = new ArrayList<>();
    private List<Integer> userEntry = new ArrayList<>();
    private String hint = "";
    private boolean isVisible = false;

    @FXML
    public void initialize() {
        historyTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

        boxFieldsController.setupFields(box1, box2, box3);

        setRandomKey(logicKeyController.randomKeytoFind());

        entryUserColumn.setCellValueFactory(cell -> cell.getValue().attemptProperty());
        hintColumn.setCellValueFactory(cell -> cell.getValue().hintProperty());

        historyTable.setItems(data);

        Platform.runLater(() -> {
            Stage stage = (Stage) titleBar.getScene().getWindow();

            btnMin.setOnAction(e -> stage.setIconified(true));
            btnClose.setOnAction(e -> stage.close());

            // Drag de la fenêtre en cliquant la barre (si pas maximisée)
            titleBar.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
                if (!stage.isMaximized()) {
                    dragOffsetX = e.getScreenX() - stage.getX();
                    dragOffsetY = e.getScreenY() - stage.getY();
                }
            });
            titleBar.addEventFilter(MouseEvent.MOUSE_DRAGGED, e -> {
                if (!stage.isMaximized()) {
                    stage.setX(e.getScreenX() - dragOffsetX);
                    stage.setY(e.getScreenY() - dragOffsetY);
                }
            });
        });
    }

    @FXML
    private void toggleElement() {
        isVisible = !isVisible;
        boolean boxVisible = isVisible;
        boolean btnPlayVisible = !isVisible;

        box1.setManaged(boxVisible); box1.setVisible(boxVisible);
        box2.setManaged(boxVisible); box2.setVisible(boxVisible);
        box3.setManaged(boxVisible); box3.setVisible(boxVisible);

        btnEntry.setManaged(boxVisible); btnEntry.setVisible(boxVisible);
        historyTable.setManaged(boxVisible); historyTable.setVisible(boxVisible);

        btnPlay.setManaged(btnPlayVisible); btnPlay.setVisible(btnPlayVisible);
        winLabel.setManaged(false); winLabel.setVisible(false);
    }

    @FXML
    private void validateEntryUser() {
        setUserEntry(boxFieldsController.getListBoxFields());

        if (verifyIfAllBoxAreComplete(userEntry)) {
            setScoreResult(solveKeyController.checkIfKeyCorrect(userEntry, randomKey));
            setHintInInfoLabel(scoreResult);
            addToHistoryTable();
        }

        if (solveKeyController.verifyIfItsOk(scoreResult)) {
            winGame();
        }
    }

    @FXML
    private void replay() {
        restartTextFields();
        data.clear();
        scoreResult = new ArrayList<>();
        setRandomKey(logicKeyController.randomKeytoFind());
        btnReplay.setVisible(false);
        btnReplay.setManaged(false);

        isVisible = false;
        toggleElement();
    }

    private boolean verifyIfAllBoxAreComplete(List<Integer> userEntry) {
        return userEntry.size() == 3;
    }

    private void setHintInInfoLabel(List<Integer> scoreResult) {
        hint = solveKeyController.hint(scoreResult).toString();
    }

    private void setScoreResult(List<Integer> testResult) {
        this.scoreResult = testResult;
    }

    private void setRandomKey(List<Integer> randomKeyGenerated) {
        this.randomKey = randomKeyGenerated;
    }

    private void setUserEntry(List<Integer> userEntry) {
        this.userEntry = userEntry;
    }

    private void winGame() {
        box1.setManaged(false); box1.setVisible(false);
        box2.setManaged(false); box2.setVisible(false);
        box3.setManaged(false); box3.setVisible(false);

        btnEntry.setVisible(false); btnEntry.setManaged(false);
        btnReplay.setVisible(true); btnReplay.setManaged(true);
        historyTable.setManaged(false); historyTable.setVisible(false);
        winLabel.setManaged(true); winLabel.setVisible(true);
    }

    private void restartTextFields() {
        box1.setText("");
        box2.setText("");
        box3.setText("");
    }

    private void addToHistoryTable() {
        data.add(new HistoryRow(userEntry.toString(), hint));
        historyTable.scrollTo(data.size() - 1);
    }

    public static class HistoryRow {
        private final SimpleStringProperty attempt = new SimpleStringProperty();
        private final SimpleStringProperty hint = new SimpleStringProperty();

        public HistoryRow(String attempt, String hint) {
            this.attempt.set(attempt);
            this.hint.set(hint);
        }
        public String getAttempt() { return attempt.get(); }
        public SimpleStringProperty attemptProperty() { return attempt; }

        public String getHint() { return hint.get(); }
        public SimpleStringProperty hintProperty() { return hint; }
    }
}
