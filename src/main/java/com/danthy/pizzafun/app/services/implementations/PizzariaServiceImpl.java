package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.events.*;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.SupplierModel;
import javafx.application.Platform;
import javafx.collections.ObservableList;

public class PizzariaServiceImpl implements IPizzariaService {
    private PizzariaState pizzariaState;

    @Override
    public int getTokens() {
        return pizzariaState.getTokensObservable().getValue();
    }

    @Override
    public void addOrder(OrderWrapper orderWrapper) {
        Platform.runLater(() -> {
            pizzariaState.getOrderModelObservableList().add(orderWrapper);
        });
    }

    @Override
    public void removeOrder(OrderWrapper orderWrapper) {
        Platform.runLater(() -> {
            pizzariaState.getOrderModelObservableList().remove(orderWrapper);

            pizzariaState.incBalance(orderWrapper.getOrderModel().getPizzaModel().getPriceToSell());
            pizzariaState.incTokens(1);
        });
    }

    @Override
    public ObservableValue<Double> getBalanceObservable() {
        return pizzariaState.getBalanceObservable();
    }

    @Override
    public ObservableValue<Integer> getTokensObservable() {
        return pizzariaState.getTokensObservable();
    }

    @Override
    public ObservableList<OrderWrapper> getOrderModelObservableList() {
        return pizzariaState.getOrderModelObservableList();
    }

    @Override
    public ObservableList<PizzaModel> getOwnedPizzaModelObservableList() {
        return pizzariaState.getOwnedPizzaModelObservableList();
    }

    @Override
    public int getOrdersAmount() {
        return pizzariaState.getOrderModelObservableList().size();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            this.pizzariaState = GetIt.getInstance().find(PizzariaState.class);
        } else if (event.getClass() == ProducedOrderEvent.class) {
            ProducedOrderEvent producedOrderEvent = (ProducedOrderEvent) event;

            removeOrder(producedOrderEvent.orderWrapper());
        } else if (event.getClass() == OrderGenerateEvent.class) {
            OrderGenerateEvent orderGenerateEvent = (OrderGenerateEvent) event;

            addOrder(new OrderWrapper(orderGenerateEvent.orderWrapper()));
        } else if (event.getClass() == ReStockEvent.class) {
            SupplierModel supplierModel = ((ReStockEvent) event).supplierModel();

            pizzariaState.decBalance(supplierModel.getCost());
        } else if (event.getClass() == SetSupplierEvent.class) {
            SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;

            int buyToken = setSupplierEvent.supplierModel().getBuyToken();

            pizzariaState.decTokens(buyToken);
        } else if (event.getClass() == LearnRecipeEvent.class) {
            LearnRecipeEvent learnRecipeEvent = (LearnRecipeEvent) event;

            pizzariaState.decTokens((int) learnRecipeEvent.recipeWrapper().getPizzaModel().getPriceToBuyRecipe());
         }
    }
}
