package com.danthy.pizzafun.app.controllers.pizzaria.widgets.itemstockcell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.events.mediator.LockItemEvent;
import com.danthy.pizzafun.app.utils.AnimationUtil;
import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class ItemStockCellListController implements IController, IMediatorEmitter {
    @FXML
    public HBox lockContainer;

    @FXML
    private Label itemStockNameLabel;

    @FXML
    private Label itemStockQuantityLabel;

    @FXML
    private HBox cellRoot;

    @FXML
    private ImageView itemStockBg;

    private ItemStockModel itemStockModel;

    @Override
    public void initComponents() {
        cellRoot.widthProperty().addListener((observable, oldValue, newValue) -> {
            itemStockBg.setFitWidth(newValue.doubleValue());
        });

        AnimationUtil.zoomOutInOnHover(cellRoot, 0.7, 1.05, 1.05);
    }

    @FXML
    public void onLockItemEvent(MouseEvent event) {
        sendEvent(new LockItemEvent(itemStockModel));
    }

    public void initCell(ItemStockModel itemStockModel) {
        this.itemStockModel = itemStockModel;
        ItemModel itemModel = this.itemStockModel.getItemModel();

        itemStockNameLabel.setText(itemModel.getName());
        itemStockQuantityLabel.setText(itemStockModel.getQuantity() + "x");

        if (this.itemStockModel.isLocked())
            lockContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);");
    }
}
