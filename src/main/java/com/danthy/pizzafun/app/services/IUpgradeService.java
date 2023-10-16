package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.ObservableList;

public interface IUpgradeService extends IService {
    void upgrade(UpgradeType upgradeType);

    int getLevel(UpgradeType upgradeType);

    ObservableList<UpgradeModel> getUpgradeModelObservableList();
}
