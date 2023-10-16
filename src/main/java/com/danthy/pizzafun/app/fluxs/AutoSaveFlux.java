package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.IFlux;
import com.danthy.pizzafun.app.events.SaveSnapshotRoomEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

public class AutoSaveFlux extends IFlux implements IMediatorEmitter {
    @Override
    public void handle() {
        int autoSaveTimeInSeconds = ApplicationProperties.roomAutoSaveTimeInSeconds;
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(autoSaveTimeInSeconds)));
        timeline.setOnFinished((event) -> {
            this.sendEvent(new SaveSnapshotRoomEvent());

            timeline.playFromStart();
        });
    }

    public void reactOnStartGameEvent(IEvent event) {
        handle();
        play();
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
