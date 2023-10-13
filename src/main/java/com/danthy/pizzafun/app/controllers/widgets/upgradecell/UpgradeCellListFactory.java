package com.danthy.pizzafun.app.controllers.widgets.upgradecell;

import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.scene.control.ListCell;

public class UpgradeCellListFactory extends ListCell<UpgradeModel> {
    @Override
    protected void updateItem(UpgradeModel item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            super.setText(null);
            super.setGraphic(null);
            return;
        }

        super.setGraphic(new UpgradeCellListWrapper().build(item));
    }

    @Override
    public void updateSelected(boolean selected) {
        if (!isEmpty()) {
            super.updateSelected(false);
        }
    }
}
