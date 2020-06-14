package controller;

import bean.Product;
import interfaces.ViewInterfaces;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.ProductService;
import strings.StringFile;

import java.sql.ResultSet;
import java.sql.SQLException;

import static connections.Connecting.*;


public class AdminMenuController implements ViewInterfaces {
    ProductService productService = new ProductService();

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private Button EnterButton;

    @FXML
    private Label infoLabel;

    @FXML
    private ComboBox<String> whatToDoBox;

    @FXML
    private Button addProductButton;

    @FXML
    private Button logOutButton;


    @FXML
    void initialize() {
        updateProductData();
        setTable();
        whatToDoBox.setItems(setWhatToDoBox());
        EnterButton.setDisable(true);
        whatToDoBox.setOnAction(event -> EnterButton.setDisable(false));
        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            updateScene("/main.fxml");
        });
        EnterButton.setOnAction(event -> {
            if (whatToDoBox.getValue().equals("Delete")) {
                TableView.TableViewSelectionModel<Product> selectionModel = ProductTable.getSelectionModel();
                selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        try {
                            startConnection();
                            statement.executeUpdate("DELETE FROM product WHERE name ='" + newValue.getName() + "'");
                            endConnection();
                            getAlert("Succsessfully", Alert.AlertType.CONFIRMATION);
                            updateProductData();
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
                whatToDoBox.setValue("What to do?");
            }
            if (whatToDoBox.getValue().equals("Update")) {
                TableView.TableViewSelectionModel<Product> selectionModel = ProductTable.getSelectionModel();
                selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        productService.getProductId(newValue.getName());
                        EnterButton.getScene().getWindow().hide();
                        updateScene("/updateProduct.fxml");
                    }
                });
                whatToDoBox.setValue("What to do?");
            }
            if (whatToDoBox.getValue().equals("Info")) {
                TableView.TableViewSelectionModel<Product> selectionModel = ProductTable.getSelectionModel();
                selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        infoLabel.setText(getInfo(newValue.getName()));
                    }
                });
                whatToDoBox.setValue("What to do?");
            }

        });
        addProductButton.setOnAction(event -> {
            addProductButton.getScene().getWindow().hide();
            updateScene("/addProduct.fxml");
        });
    }


    public void updateProductData() {
        ObservableList<Product> products = FXCollections.observableArrayList(productService.getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT));
        ProductTable.setItems(products);
    }

    public void setTable() {
        TableColumn<Product, String> name = new TableColumn<Product, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        ProductTable.getColumns().add(name);

        TableColumn<Product, String> subcategory = new TableColumn<Product, String>("Subcategory");
        subcategory.setCellValueFactory(new PropertyValueFactory<Product, String>("subcategory"));
        ProductTable.getColumns().add(subcategory);

        TableColumn<Product, Integer> count = new TableColumn<Product, Integer>("Count");
        count.setCellValueFactory(new PropertyValueFactory<Product, Integer>("amount"));
        ProductTable.getColumns().add(count);

        TableColumn<Product, Long> discount = new TableColumn<Product, Long>("Discount");
        discount.setCellValueFactory(new PropertyValueFactory<Product, Long>("discount"));
        ProductTable.getColumns().add(discount);

        TableColumn<Product, Long> price = new TableColumn<Product, Long>("Price");
        price.setCellValueFactory(new PropertyValueFactory<Product, Long>("price"));
        ProductTable.getColumns().add(price);
    }

    public ObservableList<String> setWhatToDoBox() {
        return FXCollections.observableArrayList("Delete", "Info", "Update");
    }

    public String getInfo(String id) {
        String info = null;
        try {
            startConnection();
            ResultSet resultSet = statement.executeQuery(StringFile.SELECT_ALL_FROM_PRODUCT_BY_ID + id + "'");
            while (resultSet.next()) {
                info = "Subcategory: " + resultSet.getString(2) +
                        "\nCountry: " + resultSet.getString(6) +
                        "\nName: " + resultSet.getString(1) +
                        "\nAmount: " + resultSet.getString(3) +
                        "\nDiscount: " + resultSet.getString(4) +
                        "\nPrice: " + resultSet.getString(5);
            }
            endConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return info;
    }
}

