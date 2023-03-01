module com.example.lab1_archer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.lab1_archer to javafx.fxml;
    exports com.example.lab1_archer;
}