package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.UpgradeModel;

public record LevelUpEvent(UpgradeModel upgradeModel) implements IEvent {
}
