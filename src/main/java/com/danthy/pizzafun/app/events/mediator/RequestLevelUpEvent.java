package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.UpgradeModel;

public record RequestLevelUpEvent(UpgradeModel upgradeModel) implements IEvent {
}
