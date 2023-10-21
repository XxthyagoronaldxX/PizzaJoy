package com.danthy.pizzafun.app.events.services;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.UpgradeModel;

public record ValidLevelUpEvent(UpgradeModel upgradeModel) implements IEvent {
}
