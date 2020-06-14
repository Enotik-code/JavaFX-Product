package controller;

import interfaces.ViewInterfaces;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.UserService;
import strings.StringFile;

import static service.UserService.email;

public class BasketController implements ViewInterfaces {

    UserService userService = new UserService();

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TableView<?> basketTable;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label TotalDiscountLabel;

    @FXML
    private TextField addressField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button BackButton;

    @FXML
    private Label accountLabel;

    @FXML
    void initialize() {
        setUserInfo();
        BackButton.setOnAction(event -> {
            BackButton.getScene().getWindow().hide();
            updateScene("/userMenu.fxml");
        });
    }

    public void setUserInfo() {
        emailLabel.setText("Email: " + email);
        surnameLabel.setText("Surname: " + userService.getNameOrSurnameByEmail(StringFile
                .SELECT_SURNAME_BY_EMAIL + "'" + email + "'"));
        nameLabel.setText("Name: " + userService.getNameOrSurnameByEmail(StringFile
                .SELECT_NAME_BY_EMAIL + "'" + email + "'"));
    }
}

