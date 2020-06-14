package controller;

import interfaces.ViewInterfaces;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import service.CountryService;
import strings.StringFile;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static connections.Connecting.*;
import static service.UserService.email;

public class RegistrationController implements ViewInterfaces {
    CountryService countryService = new CountryService();
    @FXML
    private RadioButton termsButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField nameFiled;

    @FXML
    private TextField surnameFiled;

    @FXML
    private TextField patronymicFiled;

    @FXML
    private DatePicker birthdaypicker;

    @FXML
    private TextField emailFiled;

    @FXML
    private TextField passwordFiled;

    @FXML
    private TextField phoneFiled;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    private Hyperlink termsLink;

    @FXML
    private Button signInButton;

    @FXML
    void initialize() throws SQLException {
        ObservableList<String> langs = FXCollections.observableArrayList(countryService.getListCountry());
        countryBox.setItems(langs);
        signInButton.setDisable(true);
        Label lbl = new Label();
        final int[] countryId = {0};
        countryBox.setOnAction(event -> {
            try {
                lbl.setText(countryBox.getValue());
                startConnection();
                ResultSet resultSet = statement.executeQuery("select * from country where Description = '" + lbl.getText() + "'");
                while (resultSet.next()) {
                    countryId[0] = resultSet.getInt(1);
                }
                resultSet.close();
                endConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        termsLink.setOnAction(event -> {

            Stage stage = new Stage();
            Label label = new Label(StringFile.terms);
            Button button = new Button("Back");
            button.setOnAction(event1 -> {
                button.getScene().getWindow().hide();
                updateScene("/registrationPage.fxml");
            });
            FlowPane root = new FlowPane(label, button);
            Scene scene = new Scene(root, 600, 650);
            stage.setScene(scene);
            stage.setTitle("Terms and Conditions");
            stage.show();
        });
        termsButton.setOnAction(event -> {
                if(signInButton.isDisable()){
                   signInButton.setDisable(false);
        }
        else{
        signInButton.setDisable(true);
        }
        });
        backButton.setOnAction(event -> {
            backButton.getScene().getWindow().hide();
            updateScene("/main.fxml");
        });
        signInButton.setOnAction(event -> {
            try {
                startConnection();
                email = emailFiled.getText();
                PreparedStatement preparedStmt = null;
                preparedStmt = connection.prepareStatement(StringFile.INSERT_INTO_USER);
                preparedStmt.setString(1, nameFiled.getText());
                preparedStmt.setString(2, surnameFiled.getText());
                preparedStmt.setString(3, patronymicFiled.getText());
                preparedStmt.setString(4, phoneFiled.getText());
                preparedStmt.setString(5, emailFiled.getText());
                preparedStmt.setString(6, passwordFiled.getText());
                preparedStmt.setInt(7, 20);
                preparedStmt.setInt(8, countryId[0]);
                preparedStmt.setString(9, null);
                preparedStmt.setDate(10, Date.valueOf(birthdaypicker.getValue()));
                preparedStmt.setDate(11, Date.valueOf(LocalDate.now()));
                preparedStmt.setDate(12, Date.valueOf(LocalDate.now()));
                preparedStmt.execute();
                endConnection();
                getAlert("We are happy to greet you.\nEnjoy the shopping.", Alert.AlertType.INFORMATION);
                signInButton.getScene().getWindow().hide();
                updateScene("/userMenu.fxml");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}

