import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Application;

import javafx.stage.Stage;

public class  App extends Application {

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("adminMenu.fxml"));
            primaryStage.setTitle("edabudet.by");
            primaryStage.setScene(new Scene(root, 605, 402));
            primaryStage.show();
        }
        public static void main(String[] args) {
            launch(args);
        }
    }
