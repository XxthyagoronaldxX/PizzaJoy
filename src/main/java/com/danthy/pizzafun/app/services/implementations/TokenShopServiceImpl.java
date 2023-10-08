package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.wrappers.SupplierWrapper;
import com.danthy.pizzafun.app.states.TokenShopState;
import javafx.collections.ObservableList;

import java.util.List;

public class TokenShopServiceImpl implements ITokenShopService, IListener {
    private TokenShopState tokenShopState;

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
        }
    }

    @Override
    public TokenShopState getTokenShopWrapper() {
        return this.tokenShopState;
    }

    @Override
    public void setCurrentSupplierWrapper(SupplierWrapper supplierWrapper) {
        tokenShopState.setCurrentSupplierWrapper(supplierWrapper);
    }
}
