package com.danthy.pizzafun.app.controllers.widgets.suppliercell;

import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import javafx.scene.control.ListCell;

public class SupplierCellListFactory extends ListCell<SupplierWrapper> {
    @Override
    protected void updateItem(SupplierWrapper item, boolean empty) {
        super.updateSelected(false);
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            super.setStyle("-fx-background-color: transparent");
            return;
        }

        super.setStyle("-fx-background-color: white");
        super.setGraphic(new SupplierCellListController(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
