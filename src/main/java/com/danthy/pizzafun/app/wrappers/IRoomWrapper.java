package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import com.danthy.pizzafun.domain.models.*;


public interface IRoomWrapper extends IWrapper<RoomModel> {
    void addOrder(OrderWrapper orderWrapper);

    void restockBySupplier(SupplierModel supplierModel);

    boolean isRemoveOrderValid(OrderModel orderModel);

    void removeItemStockFromOrder(OrderModel orderModel);

    void removeOrder(OrderWrapper orderWrapper) ;

    SupplierWrapper getSupplierWrapper() ;

    void setNextSupplierWrapper(SupplierWrapper supplierWrapper) ;

    String getBalancePrint();

    double getBalance();

    String getTokensPrint();

    int getOrdersAmount();
}
