package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.itemstockcell.ItemStockCellListFactory;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StockController extends IEmitter implements IController, IListener {
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

    private StockServiceImpl stockService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemStockList.setCellFactory(object -> new ItemStockCellListFactory());
        itemStockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        stockImageBg.fitWidthProperty().bind(rootView.prefWidthProperty());
        stockImageBg.fitHeightProperty().bind(rootView.prefHeightProperty());
    }

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        super.eventPublisher = eventPublisher;
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            stockService = GetIt.getInstance().find(StockServiceImpl.class);

            itemStockList.setItems(stockService.getStockState().getItemStockModelObservableList());

            stockService.getTimerToNextRestockProperty().addListener((observable, oldValue, newValue) -> {
                timeToNextRestockLabel.setText(String.format("%.0fs", newValue));
            });
        }
    }

    public void boostTimerToNextRestock(MouseEvent mouseEvent) {
        stockService.boostRateSpeed();
    }
}
