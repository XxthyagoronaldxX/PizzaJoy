package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.IFlux;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.util.Duration;

public class GenItemStockFlux extends IFlux implements IMediatorEmitter {
    private final StockServiceImpl stockService;
    private final TokenShopServiceImpl tokenShopService;

    public GenItemStockFlux(StockServiceImpl stockService, TokenShopServiceImpl tokenShopService) {
        this.stockService = stockService;
        this.tokenShopService = tokenShopService;
    }

    @Override
    public void handle() {
        stockService.getRateSpeedProperty().addListener((observer, oldValue, newValue) -> {
            this.timeline.setRate(newValue);
        });

        SupplierModel supplierModel = tokenShopService.getCurrentSupplierObservable().getValue();
        Property<Double> timerToNextRestockProperty = stockService.getTimerToNextRestockProperty();
        Property<Double> rateSpeedProperty = stockService.getRateSpeedProperty();

        double time = supplierModel.getDeliveryTimeInSeconds();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), new KeyValue(timerToNextRestockProperty, 0.0)));
        timeline.setRate(rateSpeedProperty.getValue());
        timeline.setOnFinished((onFinished) -> {
            Platform.runLater(() -> {this.sendEvent(new ReStockEvent(supplierModel));});

            timeline.playFromStart();
        });
    }

    public void reactOnStartGameEvent(IEvent event) {
        handle();
        play();
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
