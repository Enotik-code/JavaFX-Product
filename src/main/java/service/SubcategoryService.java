package service;

import lombok.SneakyThrows;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static connections.Connecting.*;

public class SubcategoryService {
    private final List<String> subcategoryList = new ArrayList<>();

    @SneakyThrows
    public List<String> getSubcategoryList(String sql){
        startConnection();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            subcategoryList.add(resultSet.getString(3));
        }
        endConnection();
        return subcategoryList;
    }
}
