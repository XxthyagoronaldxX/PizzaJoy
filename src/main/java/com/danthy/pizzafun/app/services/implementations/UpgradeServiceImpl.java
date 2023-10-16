package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.events.SuccessLevelUpEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.UpgradeService;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.collections.ObservableList;

public class UpgradeServiceImpl extends UpgradeService {
    private UpgradeState upgradeState;

    public UpgradeServiceImpl(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

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

    public void reactOnStartGameEvent(IEvent event) {
        upgradeState = GetIt.getInstance().find(UpgradeState.class);
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == SuccessLevelUpEvent.class) onSuccessLevelUpEvent(event);
    }

    public void onSuccessLevelUpEvent(IEvent event) {
        SuccessLevelUpEvent successLevelUpEvent = (SuccessLevelUpEvent) event;

        upgrade(successLevelUpEvent.upgradeModel().getUpgradeType());
    }
}
