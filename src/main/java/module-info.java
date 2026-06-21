module com.example.miniproyecto_50zo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.miniproyecto_50zo to javafx.fxml;
    exports com.example.miniproyecto_50zo;
}