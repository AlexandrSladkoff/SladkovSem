module com.example.sladkovsem {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.google.gson;
    opens com.example.sladkovsem to javafx.fxml;
    exports com.example.sladkovsem;
}