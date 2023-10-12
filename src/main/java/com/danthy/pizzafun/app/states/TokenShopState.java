package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.controllers.widgets.recipecell.RecipeWrapper;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenShopState {
    private final ObservableValue<SupplierModel> currentSupplierObservable;

    private final ObservableList<SupplierModel> supplierModelObservableList;

    private final ObservableList<RecipeWrapper> recipeWrapperObservableList;

    public TokenShopState(RoomModel roomModel, List<PizzaModel> pizzaModelList) {
        SupplierModel currentSupplierModel = roomModel.getSupplierModel();

        for (PizzaModel pizzaModel : roomModel.getPizzaModels())
            pizzaModelList.remove(pizzaModel);

        List<RecipeWrapper> recipeWrapperList = pizzaModelList
                .stream()
                .map(RecipeWrapper::new)
                .toList();

        this.recipeWrapperObservableList = FXCollections.observableArrayList(recipeWrapperList);
        this.currentSupplierObservable = new ObservableValue<>(currentSupplierModel);
        this.supplierModelObservableList = FXCollections.observableArrayList();
    }

    public void setCurrentSupplierWrapperObservable(SupplierModel supplierModel) {
        currentSupplierObservable.getProperty().setValue(supplierModel);
    }
}
