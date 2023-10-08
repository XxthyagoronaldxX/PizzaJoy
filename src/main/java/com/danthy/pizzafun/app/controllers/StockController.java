package com.danthy.pizzafun.app.controllers;

import com.danthy.pizzafun.app.contracts.IController;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.controllers.widgets.itemstockcell.ItemStockCellListFactory;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.implementations.RoomWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StockController implements IController, IListener {
    @FXML
    public ListView itemStockList;

    @FXML
    public AnchorPane stockView;

    @FXML
    public AnchorPane stockPaneBg;

    @FXML
    public ImageView stockImageBg;

    public double prefWidthStockView;

    @Override
    public void initialize() {
        initItemStockListView();
        initStockView();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            RoomWrapper roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

            itemStockList.setItems(roomWrapper.getItemStockModelObservableList());
        }
    }

    private void initItemStockListView() {
        itemStockList.setCellFactory(object -> new ItemStockCellListFactory());
        itemStockList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initStockView() {
        prefWidthStockView = stockView.getPrefWidth();
        stockView.setTranslateX(prefWidthStockView);
        stockView.setPrefWidth(0);

        stockPaneBg.widthProperty().addListener((observable, oldValue, newValue) -> {
            stockImageBg.setFitWidth(newValue.doubleValue());
        });
        stockPaneBg.heightProperty().addListener((observable, oldValue, newValue) -> {
            stockImageBg.setFitHeight(newValue.doubleValue());
        });
    }
}
