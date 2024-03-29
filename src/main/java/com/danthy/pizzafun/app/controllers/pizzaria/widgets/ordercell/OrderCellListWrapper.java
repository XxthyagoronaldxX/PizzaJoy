package com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class OrderCellListWrapper extends VBox {
    public VBox build(OrderWrapper orderWrapper) {
        FXMLLoader loader = FxmlUtil.loaderFromName(PathFxmlUtil.ORDER_CELL_LIST_WIDGET);

        try {
            VBox vBox = loader.load();
            OrderCellListController controller = loader.getController();
            controller.initCell(orderWrapper);

            getChildren().add(vBox);
            setVgrow(vBox, Priority.ALWAYS);
            vBox.prefHeightProperty().bind(prefHeightProperty());

            return this;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
