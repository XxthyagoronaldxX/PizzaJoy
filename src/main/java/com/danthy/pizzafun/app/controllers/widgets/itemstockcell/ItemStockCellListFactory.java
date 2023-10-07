package com.danthy.pizzafun.app.controllers.widgets.itemstockcell;

import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import javafx.scene.control.ListCell;

public class ItemStockCellListFactory extends ListCell<ItemStockWrapper> {
    @Override
    protected void updateItem(ItemStockWrapper item, boolean empty) {
        super.updateSelected(false);
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            super.setStyle("-fx-background-color: transparent;");
            return;
        }

        super.setStyle("-fx-background-color: transparent;" +
                "-fx-padding: 0px;" +
                "-fx-border-insets: 0px 6px 0px 6px;" +
                "-fx-border-width: 2px 0 0 0;" +
                "-fx-border-color: black transparent transparent transparent;");
        super.setGraphic(new ItemStockCellListController(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
