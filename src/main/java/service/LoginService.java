package service;

import controller.UserMenuController;
import interfaces.ViewInterfaces;
import javafx.scene.control.*;
import lombok.SneakyThrows;

import java.sql.ResultSet;

import static connections.Connecting.*;

public class LoginService implements ViewInterfaces {
    UserService userService = new UserService();
    @SneakyThrows
    public Integer checkLoginAndPassword(String emailSQL, String password){
        startConnection();
        String sql = "select * from user where email ='" + emailSQL + "'and password ='" + password + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getInt("idPosition") == 20) {
                userService.email = emailSQL;
                return resultSet.getInt("idPosition");
            }
            if (resultSet.getInt("idPosition") > 0 && resultSet.getInt("idPosition") <= 19) {
                userService.email = emailSQL;
                return resultSet.getInt("idPosition");
            }
            if (resultSet.getInt("idPosition") == 100) {
                return resultSet.getInt("idPosition");
            }
        }
        getAlert("Wrong login or password" ,Alert.AlertType.ERROR );
        resultSet.close();
        endConnection();
        return 0;
    }


}

