package com.danthy.pizzafun.app.controllers.widgets.suppliercell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.IOException;

public class SupplierCellListWrapper extends HBox {
    public HBox build(SupplierModel supplierModel) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.SUPPLIER_CELL_LIST_WIDGET);

        try {
            HBox hBox = loader.load();
            SupplierCellListController controller = loader.getController();
            controller.setSupplierModel(supplierModel);

            getChildren().add(hBox);
            setHgrow(hBox, Priority.ALWAYS);

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
