package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.collections.ObservableList;

public interface IPizzariaService extends IService {
    ObservableValue<Double> getBalanceObservable();

    ObservableValue<Integer> getTokensObservable();

    ObservableList<OrderWrapper> getOrderModelObservableList();

    ObservableList<PizzaModel> getOwnedPizzaModelObservableList();
}
