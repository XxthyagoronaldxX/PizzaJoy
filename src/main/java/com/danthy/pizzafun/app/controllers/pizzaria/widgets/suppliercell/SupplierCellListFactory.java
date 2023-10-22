package com.danthy.pizzafun.app.controllers.pizzaria.widgets.suppliercell;

import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.scene.control.ListCell;

public class SupplierCellListFactory extends ListCell<SupplierModel> {
    @Override
    protected void updateItem(SupplierModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            return;
        }

        super.setGraphic(new SupplierCellListWrapper().build(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
