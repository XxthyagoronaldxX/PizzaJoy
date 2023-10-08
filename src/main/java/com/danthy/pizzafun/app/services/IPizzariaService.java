package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;
import com.danthy.pizzafun.domain.models.SupplierModel;

public interface IPizzariaService extends IService {
    void addOrder(OrderWrapper orderWrapper);

    void removeOrder(OrderWrapper orderWrapper) ;

    int getOrdersAmount();
}
