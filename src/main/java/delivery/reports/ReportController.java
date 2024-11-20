package delivery.reports;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class ReportController {
    @FXML
    private Label restaurantTitle;

    @FXML
    private TableView<Order> reportTableView;

    @FXML
    private TableColumn<Order, String> itemColumn;

    @FXML
    private TableColumn<Order, Double> valueColumn;

    @FXML
    private TableColumn<Order, String> clientColumn;

    @FXML
    public void initialize() {
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
    }

    public void setReportData(String restaurant, LocalDate startDate, LocalDate endDate) {
        restaurantTitle.setText("Relatório: " + restaurant);
        ObservableList<Order> orders = FXCollections.observableArrayList(
                new Order("Pizza", 45.90, "João"),
                new Order("Hambúrguer", 25.50, "Maria"),
                new Order("Sushi", 78.00, "Carlos")
        );
        reportTableView.setItems(orders);
    }

    @FXML
    private void onBackButtonClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/delivery/reports/report-filter-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) restaurantTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}