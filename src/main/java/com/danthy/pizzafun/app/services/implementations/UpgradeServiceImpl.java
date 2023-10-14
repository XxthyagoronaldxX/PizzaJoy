package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.events.RequestLevelUpEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.events.SuccessLevelUpEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.ObservableList;

public class UpgradeServiceImpl implements IUpgradeService {
    private UpgradeState upgradeState;

    @Override
    public void upgrade(UpgradeType upgradeType) {
        for (int i = 0;i < upgradeState.getUpgradeModelObservableList().size();i++) {
            UpgradeModel upgradeModel = upgradeState.getUpgradeModelObservableList().get(i);

            if (upgradeModel.getUpgradeType() == upgradeType) {
                upgradeModel.upgrade();

                upgradeState.getUpgradeModelObservableList().set(i, upgradeModel.getClone());
            }
        }
    }

    @Override
    public int getLevel(UpgradeType upgradeType) {
        for (UpgradeModel upgradeModel : upgradeState.getUpgradeModelObservableList())
            if (upgradeModel.getUpgradeType() == upgradeType) return upgradeModel.getLevel();

        return 0;
    }

    @Override
    public ObservableList<UpgradeModel> getUpgradeModelObservableList() {
        return upgradeState.getUpgradeModelObservableList();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            upgradeState = GetIt.getInstance().find(UpgradeState.class);
        } else if (event.getClass() == SuccessLevelUpEvent.class) {
            SuccessLevelUpEvent successLevelUpEvent = (SuccessLevelUpEvent) event;

            upgrade(successLevelUpEvent.upgradeModel().getUpgradeType());
        }
    }
}
