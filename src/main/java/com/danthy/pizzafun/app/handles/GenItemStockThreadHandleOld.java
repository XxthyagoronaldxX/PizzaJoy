package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.events.GenItemStockThreadEndedEvent;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.util.Duration;

public class GenItemStockThreadHandleOld extends Thread {
    private final StockServiceImpl stockService;
    private final PizzariaServiceImpl pizzariaService;
    private final TokenShopServiceImpl tokenShopService;
    private final EventPublisher eventPublisher;
    private Timeline timeline;

    public GenItemStockThreadHandleOld() {
        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        stockService = GetIt.getInstance().find(StockServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
        tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);

        stockService.getRateSpeedProperty().addListener((observer, oldValue, newValue) -> {
            this.timeline.setRate(newValue);
        });

        setDaemon(true);
    }

    @Override
    public void run() {
        SupplierModel supplierModel = tokenShopService.getTokenShopWrapper().getCurrentSupplierWrapperObservable().getValue().getWrapped();
        Property<Double> timerToNextRestockProperty = stockService.getTimerToNextRestockProperty();
        Property<Double> rateSpeedProperty = stockService.getRateSpeedProperty();

        double time = supplierModel.getDeliveryTimeInSeconds();

        timeline = new Timeline(new KeyFrame(Duration.seconds(time), new KeyValue(timerToNextRestockProperty, 0.0)));
        timeline.setRate(rateSpeedProperty.getValue());
        timeline.setOnFinished((onFinished) -> {
            Platform.runLater(() -> {
                PizzariaState pizzariaState = pizzariaService.getPizzariaState();

                if (pizzariaState.getBalanceObservable().getValue() > supplierModel.getCost()) {
                    eventPublisher.notifyAll(new ReStockEvent(supplierModel));
                }
            });
        });

        timeline.play();

        while (timeline.getStatus() == Animation.Status.RUNNING) Thread.onSpinWait();

        eventPublisher.notifyAll(new GenItemStockThreadEndedEvent());
    }
}
