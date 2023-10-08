package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.implementations.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

public class GenItemStockThreadHandle extends Thread implements IListener {
    private RoomWrapper roomWrapper;
    private Timeline timeline;
    private volatile boolean flag = true;

    @Override
    public void run() {
        roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

        while (true) {
            SupplierWrapper supplierWrapper = roomWrapper.getSupplierWrapper();
            SupplierModel supplierModel = supplierWrapper.getSupplierModel();

            flag = true;
            double time = supplierModel.getDeliveryTimeInSeconds();

            timeline = new Timeline(new KeyFrame(Duration.seconds(time)));
            timeline.setRate(supplierWrapper.rateSpeed);
            timeline.setOnFinished((event) -> {
                flag = false;

                if (roomWrapper.getBalance() > supplierModel.getCost()) {
                    Platform.runLater(() -> {
                        GetIt.getInstance().find(EventPublisher.class).notifyAll(new ReStockEvent(supplierModel));
                    });
                }
            });

            timeline.play();
            while (flag) Thread.onSpinWait();
        }
    }

    public void burnTimePerClick() {
        SupplierWrapper supplierWrapper = roomWrapper.getSupplierWrapper();

        supplierWrapper.increaseRateSpeed(0.1);

        timeline.setRate(supplierWrapper.rateSpeed);
    }

    @Override
    public void update(IEvent event) {
    }
}
