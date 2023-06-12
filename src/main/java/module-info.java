module com.example.sgoclint {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sgoclint to javafx.fxml;
    exports com.example.sgoclint;
}