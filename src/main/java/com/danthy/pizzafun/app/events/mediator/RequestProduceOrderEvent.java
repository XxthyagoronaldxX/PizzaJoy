package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell.OrderCellListController;
import com.danthy.pizzafun.app.controllers.pizzaria.widgets.ordercell.OrderWrapper;

public record RequestProduceOrderEvent(OrderWrapper orderWrapper, OrderCellListController cellController) implements IEvent {
}
