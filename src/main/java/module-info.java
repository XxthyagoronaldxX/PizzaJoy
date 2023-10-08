module com.danthy.pizzafun {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires eu.hansolo.fx.countries;
    requires eu.hansolo.fx.heatmap;
    requires eu.hansolo.toolbox;
    requires eu.hansolo.toolboxfx;
    requires javafx.base;
    requires com.google.gson;
    requires lombok;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
        
    opens com.danthy.pizzafun to javafx.fxml;
    exports com.danthy.pizzafun;
    exports com.danthy.pizzafun.app.controllers;
    opens com.danthy.pizzafun.domain.models to com.google.gson;
    opens com.danthy.pizzafun.app.controllers to javafx.fxml;
    opens com.danthy.pizzafun.app.controllers.widgets.itemstockcell to javafx.fxml;
    exports com.danthy.pizzafun.app.controllers.widgets.ordercell;
    opens com.danthy.pizzafun.app.controllers.widgets.ordercell to javafx.fxml;
    opens com.danthy.pizzafun.app.controllers.widgets.suppliercell to javafx.fxml;
    exports com.danthy.pizzafun.app.wrappers;
    opens com.danthy.pizzafun.app.wrappers to javafx.fxml;
    exports com.danthy.pizzafun.app.states;
    opens com.danthy.pizzafun.app.states to javafx.fxml;
}