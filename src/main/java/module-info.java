module twentyone {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;

    opens twentyone to javafx.fxml;
    exports twentyone;
    opens twentyone.Controllers to javafx.fxml;
    exports twentyone.Controllers;
    opens twentyone.Classes to javafx.fxml;
    exports twentyone.Classes;
}
