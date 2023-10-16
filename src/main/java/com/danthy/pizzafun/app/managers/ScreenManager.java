package com.danthy.pizzafun.app.managers;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IManager;
import com.danthy.pizzafun.app.enums.ScreenType;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager implements IManager {

    private final Map<ScreenType, Scene> screens;
    private final Stage stage;
    private Scene currentScreen;

    public ScreenManager(Stage stage) {
        this.stage = stage;
        this.screens = new HashMap<>();
    }

    public static ScreenManager build(Stage stage) {
        return new ScreenManager(stage);
    }

    public void start() {
        refresh();
        stage.show();
    }

    public void refresh() {
        stage.setScene(currentScreen);
    }

    private void setCurrentScreen(ScreenType screenType) {
        this.currentScreen = this.screens.get(screenType);
    }

    public ScreenManager setInit(ScreenType type) {
        setCurrentScreen(type);

        return this;
    }

    public ScreenManager addScreen(ScreenType type, Scene screen) {
        this.screens.put(type, screen);

        return this;
    }

    public void reactOnStartGameEvent(IEvent event) {
        setCurrentScreen(ScreenType.ROOM);
        refresh();
    }
}

