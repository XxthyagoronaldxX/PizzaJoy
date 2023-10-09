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
            return;
        }

        super.setGraphic(new ItemStockCellListWrapper().build(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
