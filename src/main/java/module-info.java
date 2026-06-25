module com.example.miniproyecto_50zo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.junit.jupiter.api;

    opens com.example.miniproyecto_50zo to javafx.fxml;
    opens com.example.miniproyecto_50zo.controller to javafx.fxml;
    opens com.example.miniproyecto_50zo.view to javafx.fxml;
    opens com.example.miniproyecto_50zo.model to javafx.fxml;

    exports com.example.miniproyecto_50zo;
}
