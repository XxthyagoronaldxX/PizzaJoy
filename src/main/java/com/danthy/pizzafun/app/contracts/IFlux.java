package com.danthy.pizzafun.app.contracts;

import javafx.animation.Timeline;

public abstract class IFlux {
    protected Timeline timeline;

    protected IFlux() {
        timeline = new Timeline();
    }

    public abstract void handle();

    public void stop() {
        timeline.setCycleCount(0);
    }

    public void abort() {
        timeline.stop();
    }

    public void play() {
        timeline.play();
    }
}
