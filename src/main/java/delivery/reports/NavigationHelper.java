package delivery.reports;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationHelper {

    public static void switchTo(String fxmlFile, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource(fxmlFile));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}