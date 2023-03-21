module twentyone {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens twentyone to javafx.fxml;
    exports twentyone;
}
