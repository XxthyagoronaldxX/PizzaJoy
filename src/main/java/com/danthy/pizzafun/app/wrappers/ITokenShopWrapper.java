package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;

import java.util.List;

public interface ITokenShopWrapper {
    void addAllSupplierModel(List<SupplierWrapper> supplierModelList);
}
