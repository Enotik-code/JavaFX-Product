package service;

import connections.Connecting;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import lombok.SneakyThrows;
import strings.StringFile;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CountryService {
    private static List<String> countryList = new ArrayList<>();

    @SneakyThrows
    public List<String> getListCountry() {
        Connecting.startConnection();
        if (!countryList.isEmpty()) countryList.clear();
        ResultSet resultSet = null;
        resultSet = Connecting.statement.executeQuery(StringFile.SELECT_ALL_FROM_COUNTRY);
        while (resultSet.next()) {
            countryList.add(new String(resultSet.getString("Description")));
        }
        resultSet.close();
        Connecting.endConnection();
        return countryList;
    }

    public ListView<String> getCountryListView() {
        Label selectedCountry = new Label();
        ObservableList<String> countryListView = FXCollections.observableArrayList(getListCountry());
        ListView<String> langsListView = new ListView<String>(countryListView);
        langsListView.setPrefSize(200, 200);
        MultipleSelectionModel<String> langsSelectionModel = langsListView.getSelectionModel();
        langsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>() {

            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue) {
                selectedCountry.setText(newValue);
            }
        });
        return langsListView;
    }
}
