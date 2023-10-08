package com.danthy.pizzafun.app.controllers.widgets.itemstockcell;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.utils.FxmlUtil;
import com.danthy.pizzafun.app.utils.PathFxmlUtil;
import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class ItemStockCellListController extends AnchorPane  {
    @FXML
    private Label itemStockNameLabel;

    @FXML
    private Label itemStockQuantityLabel;

    @FXML
    private AnchorPane cellRoot;

    @FXML
    private ImageView itemStockBg;

    private final ItemStockWrapper itemStockWrapper;

    public ItemStockCellListController(ItemStockWrapper itemStockWrapper) {
        this.itemStockWrapper = itemStockWrapper;

        FxmlUtil.loadFXMLInjectController(this, PathFxmlUtil.ITEM_STOCK_CELL_LIST_WIDGET);
        initialize();
    }

    private void initialize() {
        itemStockNameLabel.setText(itemStockWrapper.getName());
        itemStockQuantityLabel.setText(itemStockWrapper.getQuantityPrint());

        cellRoot.widthProperty().addListener((observable, oldValue, newValue) -> {
            itemStockBg.setFitWidth(newValue.doubleValue());
        });;

        getChildren().add(cellRoot);
        setLeftAnchor(cellRoot, 0.0);
        setRightAnchor(cellRoot, 0.0);
    }
}
