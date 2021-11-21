module com.github.chamexxxx.meetingroombookingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.calendarfx.view;
    requires java.sql;
    requires ormlite.core;
    requires ormlite.jdbc;
    requires bcrypt;
    requires org.jetbrains.annotations;

    opens com.github.chamexxxx.meetingroombookingsystem to javafx.fxml;
    exports com.github.chamexxxx.meetingroombookingsystem;
    opens com.github.chamexxxx.meetingroombookingsystem.control to javafx.fxml;
    exports com.github.chamexxxx.meetingroombookingsystem.control;
    opens com.github.chamexxxx.meetingroombookingsystem.models to ormlite.core;
    exports com.github.chamexxxx.meetingroombookingsystem.models;
    opens com.github.chamexxxx.meetingroombookingsystem.dao to ormlite.core;
    exports com.github.chamexxxx.meetingroombookingsystem.dao;
    exports com.github.chamexxxx.meetingroombookingsystem.controllers;
    opens com.github.chamexxxx.meetingroombookingsystem.controllers to javafx.fxml;
}