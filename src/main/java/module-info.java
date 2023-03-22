module twentyone {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens twentyone to javafx.fxml;
    exports twentyone;
}
