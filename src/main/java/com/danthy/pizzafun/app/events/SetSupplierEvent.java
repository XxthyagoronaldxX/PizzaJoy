package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;

public record SetSupplierEvent(SupplierWrapper supplierWrapper) implements IEvent {
}
