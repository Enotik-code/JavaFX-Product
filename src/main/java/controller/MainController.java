package controller;

import interfaces.ViewInterfaces;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController implements ViewInterfaces {
    @FXML
    private Button LoginButton;

    @FXML
    private Button RegistrationButton;

    @FXML
    private Button ExitButton;

    @FXML
    void initialize() {
        LoginButton.setOnAction(event -> {
            RegistrationButton.getScene().getWindow().hide();
            updateScene("/login.fxml");
        });
        RegistrationButton.setOnAction(event -> {
            RegistrationButton.getScene().getWindow().hide();
            updateScene("/registrationPage.fxml");
        });
        ExitButton.setOnAction(event -> System.exit(0));
    }
}
