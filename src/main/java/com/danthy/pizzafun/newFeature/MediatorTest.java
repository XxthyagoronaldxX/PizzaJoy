package com.danthy.pizzafun.newFeature;

import javafx.application.Application;
import javafx.stage.Stage;

public class MediatorTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ClassFinder.main(null);
    }
}
