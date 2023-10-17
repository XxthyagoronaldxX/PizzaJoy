package com.danthy.pizzafun.app.events.mediator;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.domain.models.SupplierModel;

import java.util.List;

public record  SupplierGenerateEvent(List<SupplierModel> supplierModelList) implements IEvent {
}
