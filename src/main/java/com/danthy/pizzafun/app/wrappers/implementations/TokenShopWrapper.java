package com.danthy.pizzafun.app.wrappers.implementations;

import com.danthy.pizzafun.app.wrappers.ITokenShopWrapper;
import com.danthy.pizzafun.app.wrappers.implementations.SupplierWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenShopWrapper implements ITokenShopWrapper {
    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    public TokenShopWrapper() {
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    @Override
    public void addAllSupplierModel(List<SupplierWrapper> supplierModelList) {
        this.supplierModelObservableList.clear();

        this.supplierModelObservableList.addAll(supplierModelList);
    }
}
