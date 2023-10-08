package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapper;
import com.danthy.pizzafun.domain.models.OrderModel;

public interface IOrderWrapper extends IWrapper<OrderModel> {
    boolean produceOrder();
}
