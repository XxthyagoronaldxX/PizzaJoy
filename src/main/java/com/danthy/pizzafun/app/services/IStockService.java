package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.SupplierModel;

public interface IStockService extends IService {
    void restockBySupplier(SupplierModel supplierModel);

    boolean isRemoveOrderValid(OrderModel orderModel);

    void removeItemStockFromOrder(OrderModel orderModel);
}
