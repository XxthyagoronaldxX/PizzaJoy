package com.danthy.pizzafun.app.fluxs;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IMediatorEmitter;
import com.danthy.pizzafun.app.contracts.IFlux;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.mediator.ActionsMediator;
import com.danthy.pizzafun.domain.enums.SupplierLevel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GenSupplierFlux extends IFlux implements IMediatorEmitter {
    @Override
    public void handle() {
        int maxSuppliers = ApplicationProperties.roomInitialMaxSuppliers;
        String[] supplierNames = ApplicationProperties.supplierNames;
        SupplierLevel[] supplierLevels = SupplierLevel.values();

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(ApplicationProperties.supplierGenerationBasetime)));
        timeline.setOnFinished((event) -> {
            Platform.runLater(() -> {
                List<SupplierModel> supplierModelList = new ArrayList<>();

                for (int i = 0; i < maxSuppliers; i++) {
                    String supplierName = supplierNames[(int) Math.floor((supplierNames.length - 1) * Math.random())];
                    SupplierLevel supplierLevel = supplierLevels[(int) Math.floor((supplierLevels.length - 1) * Math.random())];

                    supplierModelList.add(new SupplierModel(supplierName, supplierLevel));
                }

                this.sendEvent(new SupplierGenerateEvent(supplierModelList));
            });

            timeline.playFromStart();
        });
    }

    public void reactOnStartGameEvent(IEvent event) {
        handle();
        play();
    }

    @Override
    public void sendEvent(IEvent event) {
        GetIt.getInstance().find(ActionsMediator.class).notify(event);
    }
}

