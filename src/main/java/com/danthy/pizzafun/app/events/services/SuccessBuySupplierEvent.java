package com.danthy.pizzafun.app.events.services;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.SupplierModel;

public record SuccessBuySupplierEvent(SupplierModel supplierModel) implements IEvent {
}
