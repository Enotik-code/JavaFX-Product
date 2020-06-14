package service;

import bean.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.SneakyThrows;
import strings.StringFile;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static connections.Connecting.*;

public class ProductService {
    public static String productId;
    List<Product> productList  = new ArrayList<>();

    CountryService countryService = new CountryService();
    SubcategoryService subcategoryService = new SubcategoryService();


    @SneakyThrows
    public List<Product> getListProduct(String sql) {
        startConnection();
        if (!productList.isEmpty()) productList.clear();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            productList.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getFloat(4),
                    resultSet.getFloat(5)));
        }
        resultSet.close();
        endConnection();
        return productList;
    }

    public void getProductId(String id){
        productId = id;
    }

    public ObservableList<String> getCountryList(){
         return FXCollections.observableArrayList(countryService.getListCountry());
    }
    public ObservableList<String> getSubcategoryList(){
      return FXCollections.observableArrayList(subcategoryService
                .getSubcategoryList(StringFile.SELECT_ALL_FROM_SUBCATEGORY));
    }


}
