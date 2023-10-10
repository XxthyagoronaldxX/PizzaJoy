package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class TokenShopState {
    private final ObservableValue<SupplierWrapper> currentSupplierWrapperObservable;

    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    public TokenShopState(RoomWrapper roomWrapper) {
        SupplierModel supplierModel = roomWrapper.getWrapped().getSupplierModel();
        SupplierWrapper supplierWrapper = new SupplierWrapper(supplierModel);

        this.currentSupplierWrapperObservable = new ObservableValue<>(supplierWrapper);
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void setCurrentSupplierWrapperObservable(SupplierWrapper supplierWrapper) {
        currentSupplierWrapperObservable.getProperty().setValue(supplierWrapper);
    }
}
