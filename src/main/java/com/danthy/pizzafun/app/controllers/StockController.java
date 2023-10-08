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
import com.danthy.pizzafun.app.states.PizzariaState;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class StockController extends IEmitter implements IController, IListener {
    @FXML
    public ListView itemStockList;

    @FXML
    public AnchorPane stockView;

    @FXML
    public AnchorPane stockPaneBg;

    @FXML
    public ImageView stockImageBg;

    public double prefWidthStockView;

    private StockServiceImpl stockService;

    @Override
    public void initialize() {
        initItemStockListView();
        initStockView();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            stockService = GetIt.getInstance().find(StockServiceImpl.class);

            itemStockList.setItems(stockService.getStockState().getItemStockModelObservableList());
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

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        super.eventPublisher = eventPublisher;
    }
}
