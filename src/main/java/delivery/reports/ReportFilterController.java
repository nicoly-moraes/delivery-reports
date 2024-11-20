package delivery.reports;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class ReportFilterController {
    @FXML
    private ComboBox<String> restaurantComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    public void initialize() {
        restaurantComboBox.getItems().addAll(
                "Restaurante A",
                "Restaurante B",
                "Restaurante C",
                "Restaurante D",
                "Restaurante E"
        );
    }

    @FXML
    private void onGenerateReportClick() {
        String selectedRestaurant = restaurantComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (selectedRestaurant == null || startDate == null || endDate == null) {
            System.out.println("Por favor, preencha todos os campos.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/delivery/reports/report-view.fxml"));
            Parent root = loader.load();
            ReportController reportController = loader.getController();
            reportController.setReportData(selectedRestaurant, startDate, endDate);
            Stage stage = (Stage) restaurantComboBox.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}