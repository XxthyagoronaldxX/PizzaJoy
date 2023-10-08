package com.danthy.pizzafun.app.events;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;

import java.util.List;

public record  SupplierGenerateEvent(List<SupplierWrapper> supplierWrapperList) implements IEvent {
}
