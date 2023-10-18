package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.Flux;
import com.danthy.pizzafun.app.events.mediator.SaveSnapshotRoomEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class AutoSaveFlux extends Flux implements IMediatorEmitter {
    @Override
    public void initFlux() {
        int autoSaveTimeInSeconds = ApplicationProperties.roomAutoSaveTimeInSeconds;
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(autoSaveTimeInSeconds)));
    }

    @Override
    public void onFinished(ActionEvent event) {
        this.sendEvent(new SaveSnapshotRoomEvent());
    }

    public void reactOnSaveSnapshotRoomEvent(IEvent event) {play();}

    public void reactOnStartGameEvent(IEvent event) {
        play();
    }
}
