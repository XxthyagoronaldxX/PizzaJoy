package com.danthy.pizzafun.app.utils;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class AnimationUtil {
    public static void zoomOutInOnHover(Node node, double seconds) {
        node.setOnMouseEntered((event) -> {
            ScaleTransition scaleTransitionIn = new ScaleTransition(Duration.seconds(seconds), node);
            scaleTransitionIn.setFromX(node.getScaleX());
            scaleTransitionIn.setFromY(node.getScaleY());
            scaleTransitionIn.setToX(1.2);
            scaleTransitionIn.setToY(1.2);
            scaleTransitionIn.setAutoReverse(true);
            scaleTransitionIn.setCycleCount(1);
            scaleTransitionIn.play();
        });

        node.setOnMouseExited((event) -> {
            ScaleTransition scaleTransitionOut = new ScaleTransition(Duration.seconds(seconds), node);
            scaleTransitionOut.setFromX(node.getScaleX());
            scaleTransitionOut.setFromY(node.getScaleY());
            scaleTransitionOut.setToX(1);
            scaleTransitionOut.setToY(1);
            scaleTransitionOut.setAutoReverse(true);
            scaleTransitionOut.setCycleCount(1);
            scaleTransitionOut.play();
        });
    }
}
