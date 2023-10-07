package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.utils.EventPublisher;
import com.danthy.pizzafun.app.utils.GetIt;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.application.Platform;

import java.util.concurrent.TimeUnit;

public class GenItemStockThreadHandle extends Thread implements IListener {
    private SupplierModel supplierModel;

    @Override
    public void run() {
        this.supplierModel = GetIt.getInstance().find(SupplierModel.class);

        while (true) {
            try {
                int time = (int) (supplierModel.getDeliveryTimeInSeconds() * 1000);

                TimeUnit.MILLISECONDS.sleep(time);

                Platform.runLater(() -> {
                    GetIt.getInstance().find(EventPublisher.class).notifyAll(new ReStockEvent(supplierModel));
                });
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == SetSupplierEvent.class) {
            SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

            this.supplierModel = setSupplierEvent.supplierModel();
        }
    }
}
