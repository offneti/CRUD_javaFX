module com.example.crudfx_neti {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens com.example.crudfx_neti to javafx.fxml;
    exports com.example.crudfx_neti;
    exports Professors;
    exports Moduls;
    opens Professors to javafx.fxml;
    exports Alumnos;
    opens Alumnos to javafx.fxml;
    opens Moduls to javafx.fxml;
}