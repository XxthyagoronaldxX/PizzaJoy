package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.handles.GenItemStockThreadHandle;
import com.danthy.pizzafun.app.services.ISupplierWrapperService;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;

public class SupplierWrapperServiceImpl implements ISupplierWrapperService {
    private final SupplierWrapper supplierWrapper;
    private final GenItemStockThreadHandle genItemStockThreadHandle;

    public SupplierWrapperServiceImpl(SupplierWrapper supplierWrapper, GenItemStockThreadHandle genItemStockThreadHandle) {
        this.supplierWrapper = supplierWrapper;
        this.genItemStockThreadHandle = genItemStockThreadHandle;
    }

    @Override
    public void increaseRateSpeed(double rateSpeed) {
        genItemStockThreadHandle.burnTimePerClick();
    }

    @Override
    public String getCostPrint() {
        return null;
    }

    @Override
    public String getNamePrint() {
        return null;
    }

    @Override
    public String getDeliveryTimeInSecondsPrint() {
        return null;
    }

    @Override
    public String getBonusChancePrint() {
        return null;
    }

    @Override
    public String getBuyTokenPrint() {
        return null;
    }

    @Override
    public int getBuyToken() {
        return 0;
    }
}
