package com.danthy.pizzafun;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.events.GotoRoomEvent;
import com.danthy.pizzafun.app.events.GotoUpgradeEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ScreenManager implements IListener {

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

    @Override
    public void update(IEvent event) {
        if (event.getClass() == GotoUpgradeEvent.class) {
            setCurrentScreen(ScreenType.UPGRADE);
            refresh();
        } else if (event.getClass() == GotoRoomEvent.class || event.getClass() == StartGameEvent.class) {
            setCurrentScreen(ScreenType.ROOM);
            refresh();
        }
    }
}

