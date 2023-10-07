package com.danthy.pizzafun.app.controllers.widgets.itemstockcell;

import com.danthy.pizzafun.app.utils.FxmlUtil;
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

public class ItemStockCellListController extends AnchorPane {
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

        loadFXML();
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

    private void animate() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.2), this);
        translateTransition.setFromX((-1) * getWidth());
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.2), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeTransition);
        parallelTransition.play();
    }

    private void loadFXML() {
        FXMLLoader loader = FxmlUtil.loaderFromName("widgets/item-stock-cell-list-widget");

        try {
            loader.setController(this);

            loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
