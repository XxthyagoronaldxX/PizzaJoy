package com.danthy.pizzafun.app.contracts;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public interface IController extends Initializable {
    @Override
    default void initialize(URL url, ResourceBundle resourceBundle) {
        initComponents();
        initListeners();
        initActionEvents();
        initAnimations();
    }

    default void initListeners() {}

    default void initActionEvents() {}

    default void initAnimations() {}

    default void initComponents() {}
}
