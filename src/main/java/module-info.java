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
    requires jakarta.xml.bind;
    requires org.eclipse.persistence.moxy;
    requires com.sun.tools.xjc;
    requires commons.beanutils;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.danthy.pizzafun to javafx.fxml, java.compiler;
    opens com.danthy.pizzafun.domain.models to com.google.gson, jakarta.xml.bind, org.eclipse.persistence.core;
    opens com.danthy.pizzafun.app.controllers.home to javafx.fxml;
    opens com.danthy.pizzafun.app.controllers.pizzaria to javafx.fxml;
    opens com.danthy.pizzafun.domain.data to jakarta.xml.bind;
    opens com.danthy.pizzafun.app.controllers.pizzaria.widgets.itemstockcell to javafx.fxml;
    opens com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell to javafx.fxml;
    opens com.danthy.pizzafun.app.controllers.pizzaria.widgets.suppliercell to javafx.fxml;
    opens com.danthy.pizzafun.app.states to javafx.fxml;
    exports com.danthy.pizzafun.domain.models;
    exports com.danthy.pizzafun.app.controllers.home;
    exports com.danthy.pizzafun.app.controllers.pizzaria;
    exports com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell;
    exports com.danthy.pizzafun.app.controllers.pizzaria.widgets.upgradecell to javafx.fxml;
    exports com.danthy.pizzafun.app.controllers.pizzaria.widgets.recipecell;
    exports com.danthy.pizzafun.app.states;
    exports com.danthy.pizzafun;
    exports com.danthy.pizzafun.app.managers;
    opens com.danthy.pizzafun.app.managers to javafx.fxml;
    exports com.danthy.pizzafun.app.controllers.pizzaria.subviews;
    opens com.danthy.pizzafun.app.controllers.pizzaria.subviews to javafx.fxml;
}