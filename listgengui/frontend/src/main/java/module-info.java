module listgengui {
    requires javafx.controls;
    requires javafx.fxml;
    requires backend;

    opens listgengui to javafx.fxml;
    exports listgengui;
}
