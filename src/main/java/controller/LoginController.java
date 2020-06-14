package controller;

import interfaces.ViewInterfaces;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.LoginService;

public class LoginController implements ViewInterfaces {


    @FXML
    private Button signInButton;

    @FXML
    private TextField emailFiled;

    @FXML
    private PasswordField passwordFiled;

    @FXML
    private Button backButton;


    @FXML
    void initialize() {
        LoginService loginService = new LoginService();
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            updateScene("/main.fxml");
        });
        signInButton.setOnAction(event -> {
            if (emailFiled.getLength() == 0) getAlert("Enter login", Alert.AlertType.ERROR);
            if (passwordFiled.getLength() == 0) getAlert("Enter password", Alert.AlertType.ERROR);
            while (emailFiled.getLength() * passwordFiled.getLength() != 0) {
                if (loginService.checkLoginAndPassword(emailFiled.getText(), passwordFiled.getText()) == 100) {
                    signInButton.getScene().getWindow().hide();
                    updateScene("/adminMenu.fxml");
                }
                if (loginService.checkLoginAndPassword(emailFiled.getText(), passwordFiled.getText()) == 20) {
                    signInButton.getScene().getWindow().hide();
                    updateScene("/userMenu.fxml");
                }
                return;
            }
        });
    }

}

