package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.EventMap;
import com.danthy.pizzafun.app.contracts.IObserverEmitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.ReactOn;
import com.danthy.pizzafun.app.events.mediator.StartGameEvent;
import com.danthy.pizzafun.app.events.services.SuccessLevelUpEvent;
import com.danthy.pizzafun.app.events.services.ValidLevelUpEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.IUpgradeService;
import com.danthy.pizzafun.app.states.UpgradeState;
import com.danthy.pizzafun.domain.enums.UpgradeType;
import com.danthy.pizzafun.domain.models.UpgradeModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

public class UpgradeServiceImpl implements IUpgradeService, IObserverEmitter  {
    private UpgradeState upgradeState;

    @Override
    public int getLevel(UpgradeType upgradeType) {
        for (UpgradeModel upgradeModel : upgradeState.getUpgradeModelObservableList())
            if (upgradeModel.getUpgradeType() == upgradeType) return upgradeModel.getLevel();

        throw new RuntimeException("Upgrade not mapped yet.");
    }

    @Override
    public ObservableValue<UpgradeModel> getUpgradePizzariaObservableValue() {
        return upgradeState.getUpgradePizzariaObservableValue();
    }

    @Override
    public ObservableList<UpgradeModel> getUpgradeModelObservableList() {
        return upgradeState.getUpgradeModelObservableList();
    }

    @ReactOn(StartGameEvent.class)
    public void reactOnStartGameEvent(IEvent event) {
        upgradeState = GetIt.getInstance().find(UpgradeState.class);
    }

    @EventMap(ValidLevelUpEvent.class)
    public void onSuccessLevelUpEvent(IEvent event) {
        ValidLevelUpEvent validLevelUpEvent = (ValidLevelUpEvent) event;
        UpgradeType upgradeType = validLevelUpEvent.upgradeModel().getUpgradeType();

        if (upgradeType == UpgradeType.PIZZARIA) {
            Property<UpgradeModel> pizzariaUpgradeProperty = upgradeState.getUpgradePizzariaObservableValue().getProperty();

            pizzariaUpgradeProperty.getValue().upgrade();

            pizzariaUpgradeProperty.setValue(pizzariaUpgradeProperty.getValue().getClone());
        } else {
            for (int i = 0; i < upgradeState.getUpgradeModelObservableList().size(); i++) {
                UpgradeModel upgradeModel = upgradeState.getUpgradeModelObservableList().get(i);

                if (upgradeModel.getUpgradeType() == upgradeType) {
                    upgradeModel.upgrade();

                    upgradeState.getUpgradeModelObservableList().set(i, upgradeModel.getClone());
                }
            }
        }

        eventPublisher.notifyAll(new SuccessLevelUpEvent(validLevelUpEvent.upgradeModel()));
    }
}
