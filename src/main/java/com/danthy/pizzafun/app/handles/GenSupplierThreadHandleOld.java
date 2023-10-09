package com.danthy.pizzafun.app.handles;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.events.GenSupplierThreadEndedEvent;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.domain.enums.SupplierLevel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GenSupplierThreadHandleOld extends Thread {
    private final EventPublisher eventPublisher;

    public GenSupplierThreadHandleOld() {
        eventPublisher = GetIt.getInstance().find(EventPublisher.class);

        setDaemon(true);
    }

    @Override
    public void run() {
        int maxSuppliers = ApplicationProperties.roomInitialMaxSuppliers;
        String[] supplierNames = ApplicationProperties.supplierNames;
        SupplierLevel[] supplierLevels = SupplierLevel.values();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(ApplicationProperties.supplierGenerationBasetime)));

        timeline.setOnFinished((event) -> {
            Platform.runLater(() -> {
                List<SupplierWrapper> supplierModelList = new ArrayList<>();

                for (int i = 0; i < maxSuppliers; i++) {
                    String supplierName = supplierNames[(int) Math.floor((supplierNames.length - 1) * Math.random())];
                    SupplierLevel supplierLevel = supplierLevels[(int) Math.floor((supplierLevels.length - 1) * Math.random())];

                    supplierModelList.add(new SupplierWrapper(new SupplierModel(supplierName, supplierLevel)));
                }

                eventPublisher.notifyAll(new SupplierGenerateEvent(supplierModelList));
            });
        });

        timeline.play();

        while (timeline.getStatus() == Animation.Status.RUNNING) Thread.onSpinWait();

        eventPublisher.notifyAll(new GenSupplierThreadEndedEvent());
    }
}

