module controllers {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.web;
    requires java.net.http;
    requires org.json;
    requires org.jsoup;
    requires java.sql;

    opens controllers to javafx.fxml;
    exports controllers;
}