package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.SupplierModel;

public record ReStockEvent(SupplierModel supplierModel) implements IEvent {
}
