package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

public class GenItemStockThreadHandle extends Thread {
    @Override
    public void run() {
        RoomWrapper roomWrapper = GetIt.getInstance().find(RoomWrapper.class);

        while (true) {
            try {
                SupplierModel supplierModel = roomWrapper.getSupplierWrapper().getSupplierModel();

                int time = (int) (supplierModel.getDeliveryTimeInSeconds() * 1000);

                TimeUnit.MILLISECONDS.sleep(time);

                if (roomWrapper.getBalance() > supplierModel.getCost()) {
                    Platform.runLater(() -> {
                        GetIt.getInstance().find(EventPublisher.class).notifyAll(new ReStockEvent(supplierModel));
                    });
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
