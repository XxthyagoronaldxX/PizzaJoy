package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.app.states.TokenShopState;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import java.util.List;

public class TokenShopServiceImpl implements ITokenShopService, IListener {
    private TokenShopState tokenShopState;

    @Override
    public TokenShopState getTokenShopWrapper() {
        return this.tokenShopState;
    }

    @Override
    public void setCurrentSupplierWrapper(SupplierWrapper supplierWrapper) {
        tokenShopState.setCurrentSupplierWrapperObservable(supplierWrapper);
    }

    @Override
    public Property<SupplierWrapper> supplierWrapperProperty() {
        return tokenShopState.getCurrentSupplierWrapperObservable().getProperty();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            tokenShopState = GetIt.getInstance().find(TokenShopState.class);
        } else if (event.getClass() == SupplierGenerateEvent.class) {
            SupplierGenerateEvent supplierGenerateEvent = (SupplierGenerateEvent) event;
            List<SupplierWrapper> supplierWrapperList = supplierGenerateEvent.supplierWrapperList();

            ObservableList<SupplierWrapper> supplierWrapperObservableList = this.tokenShopState.getSupplierModelObservableList();

            supplierWrapperObservableList.clear();
            supplierWrapperObservableList.addAll(supplierWrapperList);
        } else if (event.getClass() == SetSupplierEvent.class) {
            SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

            setCurrentSupplierWrapper(setSupplierEvent.supplierWrapper());
        }
    }
}
