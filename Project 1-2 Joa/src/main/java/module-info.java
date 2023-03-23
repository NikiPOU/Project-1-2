module twentyone {
    requires javafx.controls;
    requires javafx.fxml;

    opens twentyone to javafx.fxml;
    exports twentyone;
}
