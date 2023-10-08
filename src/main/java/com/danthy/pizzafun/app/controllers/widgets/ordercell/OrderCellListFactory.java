package com.danthy.pizzafun.app.controllers.widgets.ordercell;

import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import javafx.scene.control.ListCell;

public class OrderCellListFactory extends ListCell<OrderWrapper> {
    @Override
    protected void updateItem(OrderWrapper item, boolean empty) {
        PizzariaServiceImpl roomService = GetIt.getInstance().find(PizzariaServiceImpl.class);

        super.updateSelected(false);
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            super.setStyle("-fx-background-color: transparent");
            return;
        }

        super.setStyle("-fx-background-color: white");
        super.setGraphic(new OrderCellListController(new OrderCellListModel(item, roomService)));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
