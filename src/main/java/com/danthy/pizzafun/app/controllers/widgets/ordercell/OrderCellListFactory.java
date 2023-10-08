package com.danthy.pizzafun.app.controllers.widgets.ordercell;

import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import javafx.scene.control.ListCell;

public class OrderCellListFactory extends ListCell<OrderWrapper> {
    @Override
    protected void updateItem(OrderWrapper item, boolean empty) {
        PizzariaServiceImpl pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        StockServiceImpl stockService = GetIt.getInstance().find(StockServiceImpl.class);

        super.updateSelected(false);
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            super.setStyle("-fx-background-color: transparent");
            return;
        }

        super.setStyle("-fx-background-color: white");
        super.setGraphic(new OrderCellListController(new OrderCellListModel(item, pizzariaService, stockService)));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
