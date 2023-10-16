package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.Service;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

public abstract class TokenShopService extends Service {
    protected TokenShopService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public abstract void setCurrentSupplier(SupplierModel supplierModel);

    public abstract ObservableList<RecipeWrapper> getRecipeWrapperObservableList();

    public abstract ObservableList<SupplierModel> getSupplierModelObservableList();

    public abstract ObservableValue<SupplierModel> getCurrentSupplierObservable();

    public abstract Property<SupplierModel> getCurrentSupplierProperty();
}
