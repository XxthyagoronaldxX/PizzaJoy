package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.implementations.TokenShopServiceImpl;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class GenItemStockThreadHandle extends Thread implements IListener {
    private PizzariaState pizzariaState;
    private TokenShopServiceImpl tokenShopService;
    private Timeline timeline;
    private volatile boolean flag = true;

    @Override
    public void run() {
        while (true) {
            SupplierWrapper supplierWrapper = tokenShopService.getTokenShopWrapper().getCurrentSupplierWrapper();
            SupplierModel supplierModel = supplierWrapper.getWrapped();

            flag = true;
            double time = supplierModel.getDeliveryTimeInSeconds();

            timeline = new Timeline(new KeyFrame(Duration.seconds(time)));
            timeline.setRate(supplierWrapper.rateSpeed);
            timeline.setOnFinished((onFinished) -> {
                flag = false;

                if (pizzariaState.getRoomWrapper().getWrapped().getBalance() > supplierModel.getCost()) {
                    Platform.runLater(() -> {
                        GetIt.getInstance().find(EventPublisher.class).notifyAll(new ReStockEvent(supplierModel));
                    });
                }
            });

            timeline.play();
            while (flag) Thread.onSpinWait();
        }
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == SetSupplierEvent.class) {
            onSetSupplierEvent(event);
        } else if (event.getClass() == StartGameEvent.class) {
            onStartGameEvent(event);
        }
    }

    public void onSetSupplierEvent(IEvent event) {
        SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

        timeline.setOnFinished((onFinished) -> {
            flag = false;
        });
        timeline.stop();


        tokenShopService.setCurrentSupplierWrapper(setSupplierEvent.supplierWrapper());
    }

    public void onStartGameEvent(IEvent event) {
        pizzariaState = GetIt.getInstance().find(PizzariaState.class);
        tokenShopService = GetIt.getInstance().find(TokenShopServiceImpl.class);
    }
}
