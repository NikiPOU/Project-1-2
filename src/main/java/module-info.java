module twentyone {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens twentyone to javafx.fxml;
    exports twentyone;
    opens twentyone.Controllers to javafx.fxml;
    exports twentyone.Controllers;
    opens twentyone.Interfaces to javafx.fxml;
    exports twentyone.Interfaces;
}
