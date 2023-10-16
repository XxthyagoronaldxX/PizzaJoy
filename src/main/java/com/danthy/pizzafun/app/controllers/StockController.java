package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.itemstockcell.ItemStockCellListFactory;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.implementations.IStockServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController implements IController {
    @FXML
    public ListView itemStockList;

    @FXML
    public StackPane stockView;

    @FXML
    public VBox rootView;

    @FXML
    public ImageView stockImageBg;

    @FXML
    public Label timeToNextRestockLabel;

    @FXML
    public Label boostTimeRateButton;

    @FXML
    public Label stockLimitLabel;

    public IStockService stockService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemStockList.setCellFactory(object -> new ItemStockCellListFactory());
        itemStockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        stockImageBg.fitWidthProperty().bind(rootView.prefWidthProperty());
        stockImageBg.fitHeightProperty().bind(rootView.prefHeightProperty());
    }

    public void reactOnStartGameEvent(IEvent event) {
        stockService = GetIt.getInstance().find(IStockServiceImpl.class);

        itemStockList.setItems(stockService.getItemStockModelObservableList());

        stockService.getTimerToNextRestockProperty().addListener((observable, oldValue, newValue) -> {
            timeToNextRestockLabel.setText(String.format("%.0fs", newValue));
        });

        stockService.getCurrentStockWeightProperty().addListener((obsevable, oldValue, newValue) -> {
            int totStockWeight = stockService.getTotalStockWeightProperty().getValue();

            stockLimitLabel.setText(newValue + "/" + totStockWeight);
        });

        stockService.getTotalStockWeightProperty().addListener((obsevable, oldValue, newValue) -> {
            int currentStockWeight = stockService.getCurrentStockWeightProperty().getValue();

            stockLimitLabel.setText(currentStockWeight + "/" + newValue);
        });

        int totStockWeight = stockService.getTotalStockWeightProperty().getValue();
        int currentStockWeight = stockService.getCurrentStockWeightProperty().getValue();

        stockLimitLabel.setText(currentStockWeight + "/" + totStockWeight);
        boostTimeRateButton.setOnMouseClicked(stockService::onBoostRateSpeedEvent);
    }

}
