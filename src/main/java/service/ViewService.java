package service;

import interfaces.ViewInterfaces;
import connections.Connecting;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ViewService implements ViewInterfaces {
    List<String> subcategoryList = new ArrayList<>();

    public  List<String> getSubcategoryList(String sql) throws SQLException {
        Connecting.startConnection();
        if (!subcategoryList.isEmpty()) subcategoryList.clear();
        ResultSet resultSet = Connecting.statement.executeQuery(sql);
        while (resultSet.next()) {
            subcategoryList.add(new String(resultSet.getString("Description")));
        }
        resultSet.close();
        Connecting.endConnection();
        return subcategoryList;
    }

}
