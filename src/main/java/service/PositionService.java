package service;

import bean.Position;
import connections.Connecting;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionService {
    private List<Position> positionList = new ArrayList<>();

    @SneakyThrows
    public List<Position> getPositionList(String sql){
        Connecting.startConnection();
        ResultSet resultSet = null;
        positionList.clear();
        resultSet = Connecting.statement.executeQuery(sql);
        while (resultSet.next()) {
            positionList.add(new Position(resultSet.getInt("id"),
                    resultSet.getString("description")));
        }
        resultSet.close();
        Connecting.endConnection();
        return positionList;
    }

    public void showPosition(Stage stage, String sql) throws SQLException{
        ObservableList<Position> positionObservableList = FXCollections.observableArrayList(getPositionList(sql));
        TableView<Position> tablePosition = new TableView<Position>(positionObservableList);
        tablePosition.setPrefWidth(900);
        tablePosition.setPrefHeight(150);

        TableColumn<Position, Integer> idPosition = new TableColumn<Position, Integer>("Position ID");
        idPosition.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablePosition.getColumns().add(idPosition);

        TableColumn<Position, String> positionDescription = new TableColumn<Position, String>("Description");
        positionDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tablePosition.getColumns().add(positionDescription);

        FlowPane root = new FlowPane(Orientation.VERTICAL,10, 10, tablePosition);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 1366, 700);

        stage.setScene(scene);
        stage.setTitle("LogisticCo");
        stage.show();


    }
}
