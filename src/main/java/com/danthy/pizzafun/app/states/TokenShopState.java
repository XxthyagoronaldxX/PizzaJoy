package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TokenShopState {
    @Setter
    private SupplierWrapper currentSupplierWrapper;

    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    public TokenShopState(SupplierWrapper supplierWrapper) {
        this.currentSupplierWrapper = supplierWrapper;
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }
}
