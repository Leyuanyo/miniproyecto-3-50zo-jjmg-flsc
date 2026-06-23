module com.example.miniproyecto_50zo {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.miniproyecto_50zo to javafx.fxml;
    opens com.example.miniproyecto_50zo.controller to javafx.fxml;
    opens com.example.miniproyecto_50zo.view to javafx.fxml;
    exports com.example.miniproyecto_50zo;
}