package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;

public record ProducedOrderEvent(OrderWrapper orderWrapper) implements IEvent {
}
