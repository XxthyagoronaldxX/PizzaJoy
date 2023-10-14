package com.danthy.pizzafun.app.threads;

import com.danthy.pizzafun.app.contracts.IHandle;
import com.danthy.pizzafun.app.events.GenItemStockHandleEndedEvent;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.services.implementations.PizzariaServiceImpl;
import com.danthy.pizzafun.app.services.implementations.StockServiceImpl;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.util.Duration;

public class GenItemStockThread implements IHandle {
    private final IStockService stockService;
    private final IPizzariaService pizzariaService;
    private final ITokenShopService tokenShopService;
    private final EventPublisher eventPublisher;
    private Timeline timeline;

    public GenItemStockThread() {
        pizzariaService = GetIt.getInstance().find(PizzariaServiceImpl.class);
        stockService = GetIt.getInstance().find(StockServiceImpl.class);
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);
        tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);

        stockService.getRateSpeedProperty().addListener((observer, oldValue, newValue) -> {
            this.timeline.setRate(newValue);
        });
    }

    @Override
    public void handle() {
        SupplierModel supplierModel = tokenShopService.getCurrentSupplierObservable().getValue();
        Property<Double> timerToNextRestockProperty = stockService.getTimerToNextRestockProperty();
        Property<Double> rateSpeedProperty = stockService.getRateSpeedProperty();

        double time = supplierModel.getDeliveryTimeInSeconds();

        timeline = new Timeline(new KeyFrame(Duration.seconds(time), new KeyValue(timerToNextRestockProperty, 0.0)));
        timeline.setRate(rateSpeedProperty.getValue());
        timeline.setOnFinished((onFinished) -> {
            Platform.runLater(() -> {
                eventPublisher.notifyAll(new ReStockEvent(supplierModel));

                eventPublisher.notifyAll(new GenItemStockHandleEndedEvent());
            });
        });

        timeline.play();
    }
}
