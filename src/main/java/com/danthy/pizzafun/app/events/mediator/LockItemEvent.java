package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public record LockItemEvent(ItemStockModel itemStockModel) implements IEvent {
}
