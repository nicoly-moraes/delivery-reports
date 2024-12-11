package delivery.reports.controllers;

import delivery.reports.dao.OrderDAO;
import delivery.reports.models.Order;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class ReportFilterStatusController {
    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    public void initialize() {
        statusComboBox.getItems().addAll("PENDING", "CONFIRMED", "OUT_FOR_DELIVERY", "COMPLETED", "CANCELLED");

        startDatePicker.setEditable(false);
        endDatePicker.setEditable(false);

        LocalDate today = LocalDate.now();

        startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isAfter(today) || (endDatePicker.getValue() != null && date.isAfter(endDatePicker.getValue()))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #cccccc;");
                        }
                    }
                };
            }
        });

        endDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isAfter(today) || (startDatePicker.getValue() != null && date.isBefore(startDatePicker.getValue()))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #cccccc;");
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void onGenerateReportClick() {
        String selectedStatus = statusComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (selectedStatus == null || startDate == null || endDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Atenção!");
            alert.setContentText("Por favor, preencha todos os campos.");
            alert.show();
            return;
        }

        List<Order> orders = null;
        try {
            orders = OrderDAO.findByStatusAndInterval(selectedStatus, startDate, endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/delivery/reports/views/order-report-status.jrxml");
            JasperPrint report = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(orders));

            JDialog dialog = new JDialog(new JDialog(), "Relatório Pedidos", true);
            dialog.setSize(1200, 600);

            JRViewer panel = new JRViewer(report);

            dialog.getContentPane().add(panel);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
