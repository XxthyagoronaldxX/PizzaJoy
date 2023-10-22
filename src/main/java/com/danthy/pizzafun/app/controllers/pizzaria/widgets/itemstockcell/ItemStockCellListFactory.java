package com.danthy.pizzafun.app.controllers.pizzaria.widgets.itemstockcell;

import com.danthy.pizzafun.domain.models.ItemStockModel;
import javafx.scene.control.ListCell;

public class ItemStockCellListFactory extends ListCell<ItemStockModel> {
    @Override
    protected void updateItem(ItemStockModel item, boolean empty) {
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
