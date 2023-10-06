package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.wrappers.OrderItemListWrapper;

public record ProducedOrderEvent(OrderItemListWrapper orderItemListWrapper) implements IEvent {
}
