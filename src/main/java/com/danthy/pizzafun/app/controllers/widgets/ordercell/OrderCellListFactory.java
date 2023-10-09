package com.danthy.pizzafun.app.controllers.widgets.ordercell;

import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import javafx.animation.*;
import javafx.scene.control.ListCell;
import javafx.util.Duration;

public class OrderCellListFactory extends ListCell<OrderWrapper> {
    private final static Duration ANIMATION_DURATION = Duration.millis(600);

    public OrderCellListFactory() {}

    @Override
    protected void updateItem(OrderWrapper item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            return;
        }

        playEntryAnimation();
        setGraphic(new OrderCellListController().build(item));
    }

    private void playEntryAnimation() {
        TranslateTransition translateTransition = new TranslateTransition(ANIMATION_DURATION);
        translateTransition.setNode(this);
        translateTransition.setFromY(-50);
        translateTransition.setToY(0);

        Timeline timeline = new Timeline(new KeyFrame(ANIMATION_DURATION, new KeyValue(opacityProperty(), 1)));

        setOpacity(0);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, timeline);

        parallelTransition.play();
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
