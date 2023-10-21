package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.controllers.widgets.ordercell.OrderWrapper;
import com.danthy.pizzafun.domain.models.PizzaModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class PizzariaState {
    private final ObservableValue<Double> balanceObservable;

    private final ObservableValue<Integer> tokensObservable;

    private final ObservableValue<Integer> totalOrderAmountObservable;

    private final ObservableList<OrderWrapper> orderWrapperObservableList;

    private final ObservableList<PizzaModel> ownedPizzaModelObservableList;

    public PizzariaState(RoomModel roomModel) {
        balanceObservable = new ObservableValue<>(roomModel.getBalance());
        tokensObservable = new ObservableValue<>(roomModel.getTokens());
        totalOrderAmountObservable = new ObservableValue<>(roomModel.getTotalOrderAmount());

        orderWrapperObservableList = FXCollections.observableArrayList();
        ownedPizzaModelObservableList = FXCollections.observableArrayList(roomModel.getPizzaModels());
    }

    public void addOwnedPizza(PizzaModel pizzaModel) {
        ownedPizzaModelObservableList.add(pizzaModel);
    }

    public void incTotalOrderAmount(int totalOrderAmount) {
        int currentTotalOrderAmount = totalOrderAmountObservable.getValue();

        totalOrderAmountObservable.getProperty().setValue(currentTotalOrderAmount + totalOrderAmount);
    }

    public void decBalance(double decrement) {
        double currentBalance = balanceObservable.getValue();

        balanceObservable.getProperty().setValue(currentBalance - decrement);
    }

    public void incBalance(double increment) {
        double currentBalance = balanceObservable.getValue();

        balanceObservable.getProperty().setValue(currentBalance + increment);
    }

    public void incTokens(int increment) {
        int currentTokens = tokensObservable.getValue();

        tokensObservable.getProperty().setValue(currentTokens + increment);
    }

    public void decTokens(int decrement) {
        int currentTokens = tokensObservable.getValue();

        tokensObservable.getProperty().setValue(currentTokens - decrement);
    }
}
