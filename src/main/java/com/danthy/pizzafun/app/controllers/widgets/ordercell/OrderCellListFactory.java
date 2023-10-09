package com.danthy.pizzafun.app.controllers.widgets.ordercell;

import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class OrderCellListFactory extends ListCell<OrderWrapper> {
    private final static Duration ANIMATION_DURATION = Duration.millis(300);

    public OrderCellListFactory() {
    }

    @Override
    protected void updateItem(OrderWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            return;
        }

        if (!item.isAlreadyAnimated())
            Platform.runLater(() -> playEntryAnimation(item));

        VBox vBox = new OrderCellListWrapper().build(item);
        vBox.prefHeightProperty().bind(prefHeightProperty());
        setGraphic(vBox);
    }

    private void playEntryAnimation(OrderWrapper orderWrapper) {
        TranslateTransition translateTransition = new TranslateTransition(ANIMATION_DURATION);
        translateTransition.setNode(this);
        translateTransition.setFromY(-50);
        translateTransition.setToY(0);

        Timeline timeline = new Timeline(new KeyFrame(ANIMATION_DURATION, new KeyValue(opacityProperty(), 1)));

        setOpacity(0);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timeline);

        parallelTransition.play();

        parallelTransition.setOnFinished((event) -> {
            orderWrapper.setAlreadyAnimated(true);
        });
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
