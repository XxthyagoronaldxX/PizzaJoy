package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.Flux;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Platform;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class GenItemStockFlux extends Flux implements IMediatorEmitter {
    private final IStockService stockService;
    private final ITokenShopService tokenShopService;

    public GenItemStockFlux(IStockService stockService, ITokenShopService tokenShopService) {
        this.stockService = stockService;
        this.tokenShopService = tokenShopService;
    }

    @Override
    public void initOneTimeFlux() {
        stockService.getRateSpeedProperty().addListener((observer, oldValue, newValue) -> {
            timeline.setRate(newValue);
        });
        super.initOneTimeFlux();
    }

    @Override
    public void initFlux() {
        SupplierModel supplierModel = tokenShopService.getCurrentSupplierObservable().getValue();
        Property<Double> timerToNextRestockProperty = stockService.getTimerToNextRestockProperty();
        Property<Double> rateSpeedProperty = stockService.getRateSpeedProperty();

        double time = supplierModel.getDeliveryTimeInSeconds();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(time), new KeyValue(timerToNextRestockProperty, 0.0)));
        timeline.setRate(rateSpeedProperty.getValue());
    }

    @Override
    public void onFinished(ActionEvent event) {
        SupplierModel supplierModel = tokenShopService.getCurrentSupplierObservable().getValue();

        Platform.runLater(() -> {this.sendEvent(new ReStockEvent(supplierModel));});
    }

    public void reactOnRestockEvent(IEvent event) {
        replay();
    }

    public void reactOnStartGameEvent(IEvent event) {
        play();
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}
