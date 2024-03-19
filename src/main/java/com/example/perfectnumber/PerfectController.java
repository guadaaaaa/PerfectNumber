package com.example.perfectnumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;


public class PerfectController {

    @FXML
    private TextField numField, threadField;

    @FXML
    private HBox progressBox;

    @FXML
    private TextArea resultArea;

    private PerfectFunction finder;

    public void initialize() {
        resultArea.setEditable(false);
    }

    @FXML
    public void startFindingPerfectNumbers(ActionEvent event) {
        int num, cnt;
        try {
            num = Integer.parseInt(numField.getText());
            cnt = Integer.parseInt(threadField.getText());
            if (num < 1 || cnt < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Fill required fields");
            alert.showAndWait();
            return;
        }

        resultArea.clear();
        progressBox.getChildren().clear();

        finder = new PerfectFunction(num, cnt, resultArea, progressBox);
        finder.findPerfectNumbers();
    }
}