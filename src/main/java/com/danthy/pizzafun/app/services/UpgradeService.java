package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.Service;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.ObservableList;

public abstract class UpgradeService extends Service {
    protected UpgradeService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public abstract void upgrade(UpgradeType upgradeType);

    public abstract int getLevel(UpgradeType upgradeType);

    public abstract ObservableList<UpgradeModel> getUpgradeModelObservableList();
}
