package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.Emitter;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.events.services.SuccessBuySupplierEvent;
import com.danthy.pizzafun.app.events.mediator.SupplierGenerateEvent;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.ITokenShopService;
import com.danthy.pizzafun.app.states.TokenShopState;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

import java.util.List;

public class TokenShopServiceImpl extends Emitter implements ITokenShopService {
    private TokenShopState tokenShopState;

    public TokenShopServiceImpl(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

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

    public void reactOnStartGameEvent(IEvent event) {
        tokenShopState = GetIt.getInstance().find(TokenShopState.class);
    }

    public void reactOnSupplierGenerateEvent(IEvent event) {
        SupplierGenerateEvent supplierGenerateEvent = (SupplierGenerateEvent) event;
        List<SupplierModel> supplierModelList = supplierGenerateEvent.supplierModelList();

        ObservableList<SupplierModel> supplierWrapperObservableList = this.tokenShopState.getSupplierModelObservableList();

        supplierWrapperObservableList.clear();
        supplierWrapperObservableList.addAll(supplierModelList);
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == SuccessBuySupplierEvent.class) onSuccessBuySupplierEvent(event);
    }

    public void onSuccessBuySupplierEvent(IEvent event) {
        SuccessBuySupplierEvent successBuySupplierEvent = (SuccessBuySupplierEvent) event;

        setCurrentSupplier(successBuySupplierEvent.supplierModel());
    }
}
