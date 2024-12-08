module delivery.reports {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;

    opens delivery.reports to javafx.fxml;
    exports delivery.reports;
    exports delivery.reports.controllers;
    opens delivery.reports.controllers to javafx.fxml;
    exports delivery.reports.models;
    opens delivery.reports.models to javafx.fxml;
}