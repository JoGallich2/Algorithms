module assignment.birds {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;


/*
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
*/
    opens assignment.birds to javafx.fxml;
    exports assignment.birds;
}