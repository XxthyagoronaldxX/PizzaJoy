package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;

public interface IPizzariaService extends IService {
    int getTokens();

    void addOrder(OrderWrapper orderWrapper);

    void removeOrder(OrderWrapper orderWrapper) ;

    int getOrdersAmount();
}
