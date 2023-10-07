package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.OrderModel;

public record OrderGenerateEvent(OrderModel orderWrapper) implements IEvent {
}
