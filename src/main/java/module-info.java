module com.example.feedy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.feedy to javafx.fxml;
    exports com.example.feedy;
}