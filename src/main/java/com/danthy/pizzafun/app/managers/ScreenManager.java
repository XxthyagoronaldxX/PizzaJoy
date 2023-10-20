package com.danthy.pizzafun.app.managers;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IManager;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.enums.ScreenType;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ScreenManager implements IManager {
    private final Map<ScreenType, Scene> screens;
    private Stage stage;
    private Scene currentScreen;

    public ScreenManager() {
        this.screens = new HashMap<>();
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

    public void setInit(ScreenType type) {
        setCurrentScreen(type);
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        setCurrentScreen(ScreenType.ROOM);
        refresh();
    }
}

