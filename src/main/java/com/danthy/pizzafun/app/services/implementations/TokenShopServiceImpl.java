package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.events.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import java.util.List;

public class TokenShopServiceImpl implements ITokenShopService {
    private TokenShopState tokenShopState;

    @Override
    public void setCurrentSupplier(SupplierModel supplierModel) {
        tokenShopState.setCurrentSupplierObservable(supplierModel);
    }

    @Override
    public ObservableList<RecipeWrapper> getRecipeWrapperObservableList() {
        return tokenShopState.getRecipeWrapperObservableList();
    }

    @Override
    public ObservableList<SupplierModel> getSupplierModelObservableList() {
        return tokenShopState.getSupplierModelObservableList();
    }

    @Override
    public ObservableValue<SupplierModel> getCurrentSupplierObservable() {
        return tokenShopState.getCurrentSupplierObservable();
    }

    @Override
    public Property<SupplierModel> getCurrentSupplierProperty() {
        return tokenShopState.getCurrentSupplierObservable().getProperty();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            tokenShopState = GetIt.getInstance().find(TokenShopState.class);
        } else if (event.getClass() == SupplierGenerateEvent.class) {
            SupplierGenerateEvent supplierGenerateEvent = (SupplierGenerateEvent) event;
            List<SupplierModel> supplierModelList = supplierGenerateEvent.supplierModelList();

            ObservableList<SupplierModel> supplierWrapperObservableList = this.tokenShopState.getSupplierModelObservableList();

            supplierWrapperObservableList.clear();
            supplierWrapperObservableList.addAll(supplierModelList);
        } else if (event.getClass() == SetSupplierEvent.class) {
            SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

            setCurrentSupplier(setSupplierEvent.supplierModel());
        }
    }
}
