package com.danthy.pizzafun.app.controllers.widgets.ordercell;

import com.danthy.pizzafun.app.wrappers.implementations.OrderWrapper;
import javafx.scene.control.ListCell;

public class OrderCellListFactory extends ListCell<OrderWrapper> {
    @Override
    protected void updateItem(OrderWrapper item, boolean empty) {
        super.updateSelected(false);
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            super.setStyle("-fx-background-color: transparent");
            return;
        }

        super.setStyle("-fx-background-color: white");
        super.setGraphic(new OrderCellListController(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
