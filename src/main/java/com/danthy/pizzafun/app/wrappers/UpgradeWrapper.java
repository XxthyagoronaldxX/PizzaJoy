package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UpgradeWrapper {
    private final ObservableList<SupplierModel> supplierModelObservableList;

    public UpgradeWrapper() {
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void addAllSupplierModel(List<SupplierModel> supplierModelList) {
        this.supplierModelObservableList.clear();

        this.supplierModelObservableList.addAll(supplierModelList);
    }

    public ObservableList<SupplierModel> getSupplierModelObservableList() {
        return supplierModelObservableList;
    }
}
