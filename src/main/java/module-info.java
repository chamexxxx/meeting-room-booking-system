module com.github.chamexxxx.meetingroombookingsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.chamexxxx.meetingroombookingsystem to javafx.fxml;
    exports com.github.chamexxxx.meetingroombookingsystem;
}