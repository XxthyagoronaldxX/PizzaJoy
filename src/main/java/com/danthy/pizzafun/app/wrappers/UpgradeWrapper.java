package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class UpgradeWrapper {
    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    public UpgradeWrapper() {
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void addAllSupplierModel(List<SupplierWrapper> supplierModelList) {
        this.supplierModelObservableList.clear();

        this.supplierModelObservableList.addAll(supplierModelList);
    }
}
