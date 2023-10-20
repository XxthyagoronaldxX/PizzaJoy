package com.danthy.pizzafun.app.managers;

import com.danthy.pizzafun.app.enums.ScreenType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScreenBuilder {
    private final ScreenManager screenManager;

    private ScreenBuilder(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public static ScreenBuilder builder() {
        return new ScreenBuilder(new ScreenManager());
    }

    public ScreenBuilder setStage(Stage stage) {
        this.screenManager.setStage(stage);

        return this;
    }

    public ScreenBuilder addScreen(ScreenType type, Scene scene) {
        this.screenManager.getScreens().put(type, scene);

        return this;
    }

    public ScreenBuilder setInit(ScreenType type) {
        this.screenManager.setInit(type);

        return this;
    }

    public ScreenManager build() {
        return this.screenManager;
    }
}
