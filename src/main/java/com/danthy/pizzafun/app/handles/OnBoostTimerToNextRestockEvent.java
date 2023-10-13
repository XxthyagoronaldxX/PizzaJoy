package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class OnBoostTimerToNextRestockEvent implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        IStockService stockService = GetIt.getInstance().find(StockServiceImpl.class);

        stockService.boostRateSpeed();
    }
}
