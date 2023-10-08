package com.danthy.pizzafun.app.utils;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;


public class TimelineUtil {
    public static void runFunctionAfterTimeInSeconds(double timeInSeconds, EventHandler<ActionEvent> eventHandler) {
        Timeline timelineDecrease = new Timeline(new KeyFrame(Duration.seconds(timeInSeconds)));

        timelineDecrease.setOnFinished(eventHandler);

        timelineDecrease.play();
    }
}
