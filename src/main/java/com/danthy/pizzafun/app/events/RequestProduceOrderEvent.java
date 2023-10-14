package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderCellListController;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;

import java.util.function.Consumer;

public record RequestProduceOrderEvent(OrderWrapper orderWrapper, OrderCellListController cellController) implements IEvent {
}
