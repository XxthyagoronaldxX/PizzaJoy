package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import com.danthy.pizzafun.domain.models.SupplierModel;

public class SupplierWrapper implements IWrapperModel<SupplierModel> {
    private final SupplierModel supplierModel;

    public double rateSpeed = 1.0;

    public SupplierWrapper(RoomModel roomModel) {
        this.supplierModel = roomModel.getSupplierModel();
    }

    public SupplierWrapper(SupplierModel supplierModel) {
        this.supplierModel = supplierModel;
    }

    @Override
    public SupplierModel getWrapped() {
        return this.supplierModel;
    }
}
