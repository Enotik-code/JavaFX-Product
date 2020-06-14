package controller;

import interfaces.ViewInterfaces;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import lombok.SneakyThrows;
import service.ProductService;
import strings.StringFile;

import java.sql.ResultSet;
import java.sql.SQLException;

import static connections.Connecting.*;

public class UpdateProductController implements ViewInterfaces {
    ProductService productService = new ProductService();
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
    private Button updateButton;

    @FXML
    private TextArea commnetArea;

    @FXML
    private ComboBox<String> subcategoryBox;

    @FXML
    private ComboBox<String> countryBox;

    @FXML
    void initialize() {
        final int[] countryId = {0};
        final int[] subcategoryId = {0};

        countryBox.setItems(productService.getCountryList());
        subcategoryBox.setItems(productService.getSubcategoryList());
        setData(productService.productId);

        countryBox.setOnAction(event -> {
            try {
                startConnection();
                ResultSet resultSet = null;
                resultSet = statement.executeQuery("select country.Id from country where " +
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
        updateButton.setOnAction(event -> {

        });
        BackButton.setOnAction(event -> {
            BackButton.getScene().getWindow().hide();
            updateScene("/adminMenu.fxml");
        });
    }

    public int getProductId(int id) {
        return id;
    }

    @SneakyThrows
    public void setData(String productId) {
        startConnection();
        ResultSet resultSet = statement.executeQuery(StringFile.SELECT_ALL_FROM_PRODUCT_BY_ID + productId + "'");
        while (resultSet.next()) {
            subcategoryBox.setValue(resultSet.getString(2));
            countryBox.setValue(resultSet.getString(6));
            NameFiled.setText(resultSet.getString(1));
            amountFiled.setText(resultSet.getString(3));
            discountSlider.setValue(Double.parseDouble(resultSet.getString(4)));
            priceFiled.setText(resultSet.getString(5));
        }
        endConnection();
    }

}
