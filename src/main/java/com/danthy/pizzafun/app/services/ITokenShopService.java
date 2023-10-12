package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;

public interface ITokenShopService extends IService {
    TokenShopState getTokenShopState();

    void setCurrentSupplier(SupplierModel supplierModel);

    Property<SupplierModel> getCurrentSupplierProperty();
}
