package interfaces;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public interface ViewInterfaces {

    default Label getLabel(String labelText) {
        Label labelCategory = new Label(labelText);
        return labelCategory;
    }

    default TextField getTextFiled() {
        TextField textField = new TextField();
        return textField;
    }

    default PasswordField getPasswordFiled() {
        PasswordField passwordField = new PasswordField();
        return passwordField;
    }

    default DatePicker getDatePicker() {
        DatePicker datePicker = new DatePicker();
        datePicker.setShowWeekNumbers(true);
        return datePicker;
    }

    default Button getButton(String buttonText) {
        Button button = new Button(buttonText);
        return button;
    }

    default Alert getAlert(String textAlert, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(textAlert);
        alert.showAndWait();
        return alert;
    }

    default void updateScene(String path){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(path));
            loader.load();
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("edabudet.by");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
