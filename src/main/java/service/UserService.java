package service;

import bean.User;
import interfaces.ViewInterfaces;
import connections.Connecting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import strings.StringFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static connections.Connecting.*;

public class UserService implements ViewInterfaces {
    private List<User> userList = new ArrayList<>();
    CountryService countryService = new CountryService();
    public static String email;
    @SneakyThrows
    public void addUser(Stage stage){
        Label emailLabel = getLabel("Email*");
        TextField emailTextField = getTextFiled();
        Label passwordLabel = getLabel("Password*");
        PasswordField passwordTextField = getPasswordFiled();
        Label nameLabel = getLabel("Name");
        TextField nameTextField = getTextFiled();
        Label surnameLabel = getLabel("Surname");
        TextField surnameTextField = getTextFiled();
        Label patronymic = getLabel("Patronymic");
        TextField patronymicTextField = getTextFiled();
        Label phoneNumberLabel = getLabel("Phone number");
        TextField phoneNumberTextField = getTextFiled();
        Label dateOfBirthdayLabel = getLabel("Date od birthday");
        DatePicker dateOfBirthday = getDatePicker();
        Label cardNumberLabel = getLabel("Card Number");
        TextField textFieldCardNumber = getTextFiled();

        Date dateOfCreated = Date.valueOf(LocalDate.now());
        Date dateOfModified = Date.valueOf(LocalDate.now());

        ListView<String> countryList = countryService.getCountryListView();

        Button enter = getButton("Enter");


        enter.setOnAction(event -> { addUserToDatabase(nameTextField.getText(), surnameTextField.getText(),
                        patronymicTextField.getText(), phoneNumberTextField.getText(), emailTextField.getText(),
                        passwordTextField.getText(), 20, textFieldCardNumber.getText() ,
                        Date.valueOf(dateOfBirthday.getValue()), dateOfCreated, dateOfModified);
               getAlert("Successfully", Alert.AlertType.CONFIRMATION);
        });
        FlowPane root = null;
        root = new FlowPane(Orientation.VERTICAL, 5, 5,
           emailLabel,emailTextField,passwordLabel,passwordTextField, surnameLabel,surnameTextField,
              nameLabel, nameTextField, patronymic , patronymicTextField, phoneNumberLabel,
               phoneNumberTextField, cardNumberLabel, textFieldCardNumber, countryList,
                dateOfBirthdayLabel, dateOfBirthday, enter);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();
    }

    @SneakyThrows
    public void addUserToDatabase(String name, String surname, String patronymic, String number, String email,
                                  String password, int idCountry,String cardNumber, Date dateOfBirthday, Date dateOfCreated,
                                  Date dateOfModified){
        Connecting.startConnection();
        String query = " insert into user (name,surname,patronymic,number, email," +
                "password, idCountry, cardNumber, dateOfBirthday, dateOfCreated, dateOfModified)" +
                " values (?, ?, ?,?,?, ?, ?,?, ?,?, ?)";
        PreparedStatement preparedStmt = Connecting.connection.prepareStatement(query);
        preparedStmt.setString(1, name);
        preparedStmt.setString(2, surname);
        preparedStmt.setString(3, patronymic);
        preparedStmt.setString(4, number);
        preparedStmt.setString(5, email);
        preparedStmt.setString(6, password);
        preparedStmt.setInt(7, idCountry);
        preparedStmt.setString(8, cardNumber);
        preparedStmt.setDate(9, dateOfBirthday);
        preparedStmt.setDate(10, dateOfCreated);
        preparedStmt.setDate(11, dateOfModified);
        preparedStmt.execute();
    }

    @SneakyThrows
    public String getNameOrSurnameByEmail(String sql){
        startConnection();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
              return  resultSet.getString(1);
        }
        endConnection();
        return null;
    }

    @SneakyThrows
    public List<User> getUserList(String sql){
        Connecting.startConnection();
        ResultSet resultSet = null;
        userList.clear();
        resultSet = Connecting.statement.executeQuery(sql);
        while (resultSet.next()) {
            userList.add(new User(resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("patronymic"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("number"),
                    resultSet.getInt("idPosition"),
                    resultSet.getInt("idCountry"),
                    resultSet.getString("number"),
                    resultSet.getDate("dateOfBirthday"),
                    resultSet.getDate("dateOfCreated"),
                    resultSet.getDate("dateOfModified")));
        }
        resultSet.close();
        Connecting.endConnection();
        return userList;
    }
}
