package com.danthy.pizzafun.app.controllers.pizzaria.widgets.upgradecell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UpgradeCellListWrapper extends VBox {
    public VBox build(UpgradeModel upgradeModel) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.UPGRADE_CELL_LIST_WIDGET);

        try {
            AnchorPane vBox = loader.load();
            UpgradeCellListController controller = loader.getController();
            controller.setUpgradeModel(upgradeModel);

            getChildren().add(vBox);
            setVgrow(vBox, Priority.ALWAYS);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
