package controller;

import interfaces.ViewInterfaces;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.ProductService;
import strings.StringFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static connections.Connecting.*;


public class AddProductController implements ViewInterfaces {
    @FXML
    private Button BackButton;

    @FXML
    private TextField NameFiled;

    @FXML
    private TextField priceFiled;

    @FXML
    private Slider discountSlider;

    @FXML
    private TextField amountFiled;

    @FXML
    private Button addButton;

    @FXML
    private TextArea commnetArea;

    @FXML
    private ComboBox<String> subcategoryBox;

    @FXML
    private ComboBox<String> countryBox;


    @FXML
    void initialize() {
        ProductService productService = new ProductService();
        countryBox.setItems(productService.getCountryList());
        subcategoryBox.setItems(productService.getSubcategoryList());
        final int[] countryId = {0};
        final int[] subcategoryId = {0};
        countryBox.setOnAction(event -> {
            try {
                startConnection();
                ResultSet resultSet = statement.executeQuery("select country.Id from country where " +
                        "Description = '" + countryBox.getValue() + "'");
                while (resultSet.next()) {
                    countryId[0] = resultSet.getInt(1);
                }
                resultSet.close();
                endConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        subcategoryBox.setOnAction(event -> {
            try {
                startConnection();
                ResultSet resultSet = statement.executeQuery("select subcategory.Id from subcategory where " +
                        "Description = '" + subcategoryBox.getValue() + "'");
                while (resultSet.next()) {
                    subcategoryId[0] = resultSet.getInt(1);
                }
                resultSet.close();
                endConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        BackButton.setOnAction(event -> {
            BackButton.getScene().getWindow().hide();
            updateScene("/adminMenu.fxml");
        });
        addButton.setOnAction(event -> {
            startConnection();
            PreparedStatement preparedStmt = null;
            try {
                preparedStmt = connection.prepareStatement(StringFile.INSERT_INTO_PRODUCT);
                preparedStmt.setString(1, NameFiled.getText());
                preparedStmt.setInt(2, subcategoryId[0]);
                preparedStmt.setInt(3, Integer.parseInt(amountFiled.getText()));
                preparedStmt.setFloat(4, Float.parseFloat(priceFiled.getText()));
                preparedStmt.setFloat(5, (float) discountSlider.getValue());
                if((float) discountSlider.getValue() == 0)  preparedStmt.setFloat(6, Float.parseFloat(priceFiled.getText()));
                else   preparedStmt.setFloat(6, Float.parseFloat(priceFiled.getText()) * (float) discountSlider.getValue() / 100);
                preparedStmt.setInt(7, countryId[0]);
                preparedStmt.execute();
                endConnection();
                getAlert("Successfully added", Alert.AlertType.CONFIRMATION);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }
}