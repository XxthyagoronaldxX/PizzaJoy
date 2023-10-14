package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.enums.NotifyType;

public record NotifyEvent(NotifyType notifyType) implements IEvent {
}
