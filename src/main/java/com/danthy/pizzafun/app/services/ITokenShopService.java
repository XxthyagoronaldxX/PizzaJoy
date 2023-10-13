package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;

public interface ITokenShopService extends IService, IListener {
    void setCurrentSupplier(SupplierModel supplierModel);

    ObservableList<RecipeWrapper> getRecipeWrapperObservableList();

    ObservableList<SupplierModel> getSupplierModelObservableList();

    ObservableValue<SupplierModel> getCurrentSupplierObservable();

    Property<SupplierModel> getCurrentSupplierProperty();
}
