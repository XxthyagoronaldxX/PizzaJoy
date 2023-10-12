package com.danthy.pizzafun.app.controllers.widgets.itemstockcell;

import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;
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

    public void setItemStockWrapper(ItemStockModel itemStockModel) {
        ItemModel itemModel = itemStockModel.getItemModel();

        itemStockNameLabel.setText(itemModel.getName());
        itemStockQuantityLabel.setText(itemStockModel.getQuantity() + "x");
    }
}
