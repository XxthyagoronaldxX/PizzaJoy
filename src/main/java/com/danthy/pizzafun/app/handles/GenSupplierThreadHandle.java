package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import com.danthy.pizzafun.domain.enums.SupplierLevel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GenSupplierThreadHandle extends Thread {
    @Override
    public void run() {
        int maxSuppliers = ApplicationProperties.roomInitialMaxSuppliers;
        String[] supplierNames = ApplicationProperties.supplierNames;
        SupplierLevel[] supplierLevels = SupplierLevel.values();
        EventPublisher eventPublisher = GetIt.getInstance().find(EventPublisher.class);

        while (true) {
            try {
                TimeUnit.SECONDS.sleep(ApplicationProperties.supplierGenerationBasetime);

                List<SupplierWrapper> supplierModelList = new ArrayList<>();

                for (int i = 0; i < maxSuppliers; i++) {
                    String supplierName = supplierNames[(int) Math.floor((supplierNames.length - 1) * Math.random())];
                    SupplierLevel supplierLevel = supplierLevels[(int) Math.floor((supplierLevels.length - 1) * Math.random())];

                    supplierModelList.add(new SupplierWrapper(new SupplierModel(supplierName, supplierLevel)));
                }

                Platform.runLater(() -> {
                    eventPublisher.notifyAll(new SupplierGenerateEvent(supplierModelList));
                });

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

