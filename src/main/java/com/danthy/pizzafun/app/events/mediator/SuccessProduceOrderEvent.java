package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell.OrderWrapper;

public record SuccessProduceOrderEvent(OrderWrapper orderWrapper) implements IEvent {
}
