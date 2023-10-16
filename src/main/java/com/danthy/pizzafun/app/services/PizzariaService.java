package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.Service;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.PizzaModel;
import javafx.collections.ObservableList;

public abstract class PizzariaService extends Service {
    protected PizzariaService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public abstract int getTokens();

    public abstract void addOrder(OrderWrapper orderWrapper);

    public abstract void removeOrder(OrderWrapper orderWrapper);

    public abstract int getOrdersAmount();

    public abstract ObservableValue<Double> getBalanceObservable();

    public abstract ObservableValue<Integer> getTokensObservable();

    public abstract ObservableList<OrderWrapper> getOrderModelObservableList();

    public abstract ObservableList<PizzaModel> getOwnedPizzaModelObservableList();
}
