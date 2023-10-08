package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;

public interface ITokenShopService extends IService {
    TokenShopState getTokenShopWrapper();

    void setCurrentSupplierWrapper(SupplierWrapper supplierWrapper);
}
