package com.danthy.pizzafun.app.contracts;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;


public abstract class Flux {
    protected Timeline timeline;

    protected Flux() {
        timeline = new Timeline();
        timeline.setOnFinished(this::onFinished);
    }

    public void initOneTimeFlux() {

    }

    public abstract void initFlux();

    public abstract void onFinished(ActionEvent event);

    public final void stop() {
        timeline.setCycleCount(0);
    }

    public final void abort() {
        timeline.stop();
    }

    public final void replay() {
        initFlux();
        timeline.play();
    }

    public final void play() {
        initOneTimeFlux();
        initFlux();
        timeline.play();
    }
}
