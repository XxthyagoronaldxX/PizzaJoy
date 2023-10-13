package com.danthy.pizzafun.app.controllers.widgets.upgradecell;

import com.danthy.pizzafun.app.controllers.widgets.suppliercell.SupplierCellListController;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.domain.models.SupplierModel;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

public class UpgradeCellListWrapper extends HBox {
    public HBox build(UpgradeModel upgradeModel) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.UPGRADE_CELL_LIST_WIDGET);

        try {
            HBox hBox = loader.load();
            UpgradeCellListController controller = loader.getController();
            controller.setUpgradeModel(upgradeModel);

            getChildren().add(hBox);
            setHgrow(hBox, Priority.ALWAYS);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
