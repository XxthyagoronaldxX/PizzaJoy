package com.danthy.pizzafun.app.controllers.widgets.itemstockcell;

import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemStockCellListController implements Initializable {
    @FXML
    private Label itemStockNameLabel;

    @FXML
    private Label itemStockQuantityLabel;

    @FXML
    private HBox cellRoot;

    @FXML
    private ImageView itemStockBg;

    public ItemStockCellListController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cellRoot.widthProperty().addListener((observable, oldValue, newValue) -> {
            itemStockBg.setFitWidth(newValue.doubleValue());
        });
    }

    public void setItemStockWrapper(ItemStockWrapper itemStockWrapper) {
        itemStockNameLabel.setText(itemStockWrapper.getName());
        itemStockQuantityLabel.setText(itemStockWrapper.getQuantityPrint());
    }
}
