package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.PizzariaController;
import com.danthy.pizzafun.app.logic.ObservableValue;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class OnStockViewEvent implements EventHandler<MouseEvent> {
    private final AnchorPane roomView;
    private final AnchorPane stockWrapperPane;
    private final double stockViewWidth;

    public OnStockViewEvent(PizzariaController controller) {
        roomView = controller.roomView;
        stockWrapperPane = controller.stockWrapperPane;
        stockViewWidth = controller.stockViewWidth;
    }

    @Override
    public void handle(MouseEvent event) {
        Platform.runLater(() -> {
            double fromRightAnchor = AnchorPane.getRightAnchor(roomView) == 0 ? 0 : stockViewWidth;
            double toRightAnchor = AnchorPane.getRightAnchor(roomView) == 0 ? stockViewWidth : 0;

            ObservableValue<Double> rightAnchorObservable = new ObservableValue<>(fromRightAnchor);

            rightAnchorObservable.getProperty().addListener((observable, oldValue, newValue) -> {
                AnchorPane.setRightAnchor(roomView, newValue);
                AnchorPane.setRightAnchor(stockWrapperPane, (-1) * stockViewWidth + newValue);
            });

            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(rightAnchorObservable.getProperty(), toRightAnchor);

            KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
        });
    }
}
