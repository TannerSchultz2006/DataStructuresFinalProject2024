module cos.dstruct.klassroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.google.gson;
    requires com.google.common;

    opens cos.dstruct.klassroom to javafx.fxml, com.google.gson;
    opens cos.dstruct.klassroom.database to com.google.gson;
    opens cos.dstruct.klassroom.datatypes to com.google.gson;
    opens cos.dstruct.klassroom.datastructures to com.google.gson;
    exports cos.dstruct.klassroom;
    exports cos.dstruct.klassroom.controllers;
}
