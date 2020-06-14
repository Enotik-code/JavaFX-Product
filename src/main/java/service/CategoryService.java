package service;

import interfaces.ViewInterfaces;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import lombok.SneakyThrows;
import strings.StringFile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static connections.Connecting.*;

public class CategoryService implements ViewInterfaces {

    private final List<String> categoryList = new ArrayList<>();

    @SneakyThrows
    public List<String> getCategoryList(){
        startConnection();
        ResultSet resultSet = statement.executeQuery(StringFile.SELECT_ALL_FROM_CATEGORY);
        while (resultSet.next()){
            categoryList.add(resultSet.getString(2));
        }
        endConnection();
        return categoryList;
    }
}
