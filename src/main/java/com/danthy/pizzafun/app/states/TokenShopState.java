package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.wrappers.PizzaWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenShopState {
    private final ObservableValue<SupplierWrapper> currentSupplierWrapperObservable;

    private final ObservableList<SupplierWrapper> supplierModelObservableList;

    private final ObservableList<PizzaWrapper> pizzaWrapperObservableList;

    public TokenShopState(SupplierModel currentSupplier, List<PizzaModel> pizzaList) {
        SupplierWrapper supplierWrapper = new SupplierWrapper(currentSupplier);

        List<PizzaWrapper> pizzaWrapperList = pizzaList
                .stream()
                .map(PizzaWrapper::new)
                .toList();

        this.pizzaWrapperObservableList = FXCollections.observableArrayList(pizzaWrapperList);
        this.currentSupplierWrapperObservable = new ObservableValue<>(supplierWrapper);
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void setCurrentSupplierWrapperObservable(SupplierWrapper supplierWrapper) {
        currentSupplierWrapperObservable.getProperty().setValue(supplierWrapper);
    }
}
