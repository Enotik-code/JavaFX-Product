package controller;

import java.io.IOException;

import bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.CategoryService;
import service.ProductService;
import service.SubcategoryService;
import service.UserService;
import strings.StringFile;

import static service.UserService.email;

public class UserMenuController {
    ProductService productService = new ProductService();
    UserService userService = new UserService();

    @FXML
    private TableView<Product> ProductTable;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox<String> SortComboBox;

    @FXML
    private ComboBox<String> subcategoryBox;

    @FXML
    private TextField FindFiled;

    @FXML
    private Label emailLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Button FindButton;

    @FXML
    private Button addToCartButton;

    @FXML
    private Slider slider;

    @FXML
    private Button ShowCartButton;

    @FXML
    void initialize() {
        ProductService productService = new ProductService();

        getUserData();

        subcategoryBox.setItems(productService.getSubcategoryList());
        SortComboBox.setItems(getSortList());

        updateProductData();
        setTable();

        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            toNewPage("/main.fxml");
        });
        SortComboBox.setOnAction(event -> {
            if (SortComboBox.getValue().equals("Ascending")) {

                ObservableList<Product> productsByCategory = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_ASCENDING));
                ProductTable.setItems(productsByCategory);
            }
            if (SortComboBox.getValue().equals("Descending")) {
                ObservableList<Product> productsByCategory = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_ORDER_BY_PRICE_DESCENDING));
                ProductTable.setItems(productsByCategory);
            }
            if (SortComboBox.getValue().equals("Null")) {
                ObservableList<Product> productsAll = FXCollections
                        .observableArrayList(productService
                                .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT));
                ProductTable.setItems(productsAll);
            }
        });
        subcategoryBox.setOnAction(event -> {
            ObservableList<Product> productsBySubcategory = FXCollections
                    .observableArrayList(productService
                            .getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT_BY_SUBCATEGORY + "'"
                                    + subcategoryBox.getValue() + "';"));
            ProductTable.setItems(productsBySubcategory);
        });
        FindButton.setOnAction(event -> {
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
        addToCartButton.setOnAction(event -> {
            double value = slider.getValue();
        });
        ShowCartButton.setOnAction(event -> {
          ShowCartButton.getScene().getWindow().hide();
          toNewPage("/basket.fxml");
        });

    }

    public void toNewPage(String page){
        try {
            ShowCartButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(page));
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

    public void getUserData(){
        emailLabel.setText("Email: " + email);
        surnameLabel.setText("Surname: " + userService.getNameOrSurnameByEmail(StringFile
                .SELECT_SURNAME_BY_EMAIL + "'" + email + "'"));
        nameLabel.setText("Name: " + userService.getNameOrSurnameByEmail(StringFile
                .SELECT_NAME_BY_EMAIL + "'" + email + "'"));
    }

    public ObservableList<String> getSortList(){
        return FXCollections.observableArrayList("Ascending", "Descending", "Null");
    }

    public void setTable(){
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

    public void updateProductData(){
        ObservableList<Product> products = FXCollections.
                observableArrayList(productService.getListProduct(StringFile.SELECT_ALL_FROM_PRODUCT));
        ProductTable.setItems(products);
    }
}
