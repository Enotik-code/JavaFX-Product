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
import service.CountryService;
import service.ProductService;
import service.SubcategoryService;
import strings.StringFile;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static connections.Connecting.*;


public class AdminMenuController implements ViewInterfaces {
    ProductService productService = new ProductService();
    CountryService countryService = new CountryService();
    SubcategoryService subcategoryService = new SubcategoryService();

    @FXML
    private ComboBox<?> whatToDoUser;

    @FXML
    private TextField FindFiled;

    @FXML
    private ComboBox<String> subcategoryBox;

    @FXML
    private ComboBox<String> SortByBox;

    @FXML
    private ComboBox<String> CountryBox;

    @FXML
    private TextField MinPrice;

    @FXML
    private TextField MaxPrice;

    @FXML
    private TextField MinDisc;

    @FXML
    private TextField MaxDisc;

    @FXML
    private CheckBox HaveDisc;

    @FXML
    private CheckBox NoDisc;

    @FXML
    private CheckBox NoProduct;

    @FXML
    private Button enterUser;

    @FXML
    private ComboBox<?> sortBoxUser;

    @FXML
    private TextField findFiledBoxUser;

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
        SortByBox.setItems(getSortList());
        CountryBox.setItems(getCountryList());
        subcategoryBox.setItems(getSubcategoryList());
        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            updateScene("/main.fxml");
        });
        SortByBox.setOnAction(event -> {
            if (SortByBox.getValue().equals("Ascending")) {
                ObservableList<Product> productsByCategory = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_ASCENDING));
                ProductTable.setItems(productsByCategory);
            }
            if (SortByBox.getValue().equals("Descending")) {
                ObservableList<Product> productsByCategory = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_DESCENDING));
                ProductTable.setItems(productsByCategory);
            }
            if (SortByBox.getValue().equals("Null")) {
                ObservableList<Product> productsAll = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT));
                ProductTable.setItems(productsAll);
            }
        });
        subcategoryBox.setOnAction(event ->{
            ObservableList<Product> productsBySubcategory = FXCollections
                    .observableArrayList(productService
                            .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_BY_SUBCATEGORY + "'"
                                    + subcategoryBox.getValue() + "';"));
            ProductTable.setItems(productsBySubcategory);
        });
        CountryBox.setOnAction(event -> {
            ObservableList<Product> productsByCountry = FXCollections
                    .observableArrayList(productService
                            .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_BY_COUNTRY
                                    + CountryBox.getValue() + "'"));
            ProductTable.setItems(productsByCountry);
        });
        FindFiled.setOnAction(event -> {
            if (FindFiled.getText().equals("")) {
                ObservableList<Product> productsAll = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT));
                ProductTable.setItems(productsAll);
            }
            if (!FindFiled.getText().equals("")) {
                ObservableList<Product> productsFind = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile
                                        .SELECT_ALL_FROM_PRODUCT_FIND_BY_NAME + "'%" + FindFiled.getText() + "%';"));
                ProductTable.setItems(productsFind);
            }

        });
        NoDisc.setOnAction(event ->{
            ProductTable.setItems(FXCollections.observableArrayList(productService.
                    getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_NO_DISCOUNT)));
        });
        HaveDisc.setOnAction(event ->{
            ProductTable.setItems(FXCollections.observableArrayList(productService.
                    getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_HAVE_DISCOUNT)));
        });
        NoProduct.setOnAction(event -> {
            ProductTable.setItems(FXCollections.observableArrayList(productService.
                    getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_NO_PRODUCT)));
        });
        EnterButton.setOnAction(event -> {
            updateProductData();
            if (whatToDoBox.getValue().equals("Delete")) {
                TableView.TableViewSelectionModel<Product> selectionModel = ProductTable.getSelectionModel();
                selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                        try {
                            Optional<ButtonType> resultAlert = getAlert("Delete?", Alert.AlertType.CONFIRMATION).showAndWait();
                            if (resultAlert.get() == ButtonType.OK) {
                                startConnection();
                                statement.executeUpdate("DELETE FROM product WHERE name ='" + newValue.getName() + "'");
                                endConnection();
                                getAlert("Succsessfully", Alert.AlertType.INFORMATION);
                                updateProductData();
                            }
                            else{
                                updateProductData();
                            }
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                });
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
            }
            if (whatToDoBox.getValue().equals("Info")) {
                TableView.TableViewSelectionModel<Product> selectionModel = ProductTable.getSelectionModel();
                selectionModel.selectedItemProperty().addListener(new ChangeListener<Product>() {
                    @Override
                    public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
                       getAlert(getInfo(newValue.getName()), Alert.AlertType.INFORMATION);
                    }
                });
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

    public ObservableList<String> getSortList(){
        return FXCollections.observableArrayList("Ascending", "Descending", "Null");
    }

    public ObservableList<String> getCountryList(){
        return FXCollections.observableArrayList(countryService.getListCountry());
    }

    public ObservableList<String> getSubcategoryList(){
        return FXCollections.observableArrayList(subcategoryService.getSubcategoryList(StringFile.SELECT_ALL_FROM_SUBCATEGORY));
    }


}


