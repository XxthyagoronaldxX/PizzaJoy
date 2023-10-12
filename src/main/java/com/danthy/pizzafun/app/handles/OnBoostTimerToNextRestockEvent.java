package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.controllers.StockController;
import com.danthy.pizzafun.app.services.IStockService;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class OnBoostTimerToNextRestockEvent implements EventHandler<MouseEvent> {
    private final IStockService stockService;

    public OnBoostTimerToNextRestockEvent(StockController controller) {
        stockService = controller.stockService;
    }

    @Override
    public void handle(MouseEvent event) {
        stockService.boostRateSpeed();
    }
}
