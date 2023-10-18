package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.Flux;
import com.danthy.pizzafun.app.events.mediator.SupplierGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.domain.enums.SupplierLevel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GenSupplierFlux extends Flux implements IMediatorEmitter {
    @Override
    public void initFlux() {
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(ApplicationProperties.supplierGenerationBasetime)));
    }

    @Override
    public void onFinished(ActionEvent event) {
        int maxSuppliers = ApplicationProperties.roomInitialMaxSuppliers;
        String[] supplierNames = ApplicationProperties.supplierNames;
        SupplierLevel[] supplierLevels = SupplierLevel.values();

        Platform.runLater(() -> {
            List<SupplierModel> supplierModelList = new ArrayList<>();

            for (int i = 0; i < maxSuppliers; i++) {
                String supplierName = supplierNames[(int) Math.floor((supplierNames.length - 1) * Math.random())];
                SupplierLevel supplierLevel = supplierLevels[(int) Math.floor((supplierLevels.length - 1) * Math.random())];

                supplierModelList.add(new SupplierModel(supplierName, supplierLevel));
            }

            this.sendEvent(new SupplierGenerateEvent(supplierModelList));
        });
    }

    public void reactOnSupplierGenerateEvent(IEvent event) {
        play();
    }

    public void reactOnStartGameEvent(IEvent event) {
        play();
    }
}

