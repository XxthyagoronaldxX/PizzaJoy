package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;

public interface IStockService extends IService {
    Property<Double> getTimerToNextRestockProperty();

    Property<Double> getRateSpeedProperty();

    void boostRateSpeed();

    void restockBySupplier(SupplierModel supplierModel);

    boolean isRemoveOrderValid(OrderModel orderModel);

    void removeItemStockFromOrder(OrderModel orderModel);
}
