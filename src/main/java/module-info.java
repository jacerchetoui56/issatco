module com.example.feedy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.feedy to javafx.fxml;
    exports com.example.feedy;
    exports com.example.feedy.repositories;
    exports com.example.feedy.controllers;
    opens com.example.feedy.repositories to javafx.fxml;
    opens com.example.feedy.controllers to javafx.fxml;
}