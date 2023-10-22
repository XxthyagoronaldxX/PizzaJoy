package com.danthy.pizzafun.app.controllers.pizzaria.widgets.itemstockcell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

public class ItemStockCellListWrapper extends HBox {
    public HBox build (ItemStockModel itemStockWrapper) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.ITEM_STOCK_CELL_LIST_WIDGET);

        try {
            HBox hBox = loader.load();
            ItemStockCellListController controller = loader.getController();
            controller.initCell(itemStockWrapper);

            getChildren().add(hBox);
            setHgrow(hBox, Priority.ALWAYS);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
