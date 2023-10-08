package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class TokenShopState {
    private final ObservableValue<SupplierWrapper> currentSupplierWrapperObservable;

    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    public TokenShopState(SupplierWrapper supplierWrapper) {
        this.currentSupplierWrapperObservable = new ObservableValue<>(supplierWrapper);
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void setCurrentSupplierWrapperObservable(SupplierWrapper supplierWrapper) {
        currentSupplierWrapperObservable.getProperty().setValue(supplierWrapper);
    }
}
