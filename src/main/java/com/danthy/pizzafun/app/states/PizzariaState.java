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
    private final ObservableValue<Double> balanceNotifier;

    private final ObservableValue<Integer> tokenNotifier;

    private final ObservableValue<Integer> totalOrderAmountNotifier;

    private final ObservableList<OrderWrapper> orderWrapperNotifierList;

    private final ObservableList<PizzaModel> ownedPizzaModelNotifierList;

    public PizzariaState(RoomModel roomModel) {
        balanceNotifier = new ObservableValue<>(roomModel.getBalance());
        tokenNotifier = new ObservableValue<>(roomModel.getTokens());
        totalOrderAmountNotifier = new ObservableValue<>(roomModel.getTotalOrderAmount());

        orderWrapperNotifierList = FXCollections.observableArrayList();
        ownedPizzaModelNotifierList = FXCollections.observableArrayList(roomModel.getPizzaModels());
    }

    public void addOwnedPizza(PizzaModel pizzaModel) {
        ownedPizzaModelNotifierList.add(pizzaModel);
    }

    public void incTotalOrderAmount(int totalOrderAmount) {
        int currentTotalOrderAmount = totalOrderAmountNotifier.getValue();

        totalOrderAmountNotifier.getProperty().setValue(currentTotalOrderAmount + totalOrderAmount);
    }

    public void decBalance(double decrement) {
        double currentBalance = balanceNotifier.getValue();

        balanceNotifier.getProperty().setValue(currentBalance - decrement);
    }

    public void incBalance(double increment) {
        double currentBalance = balanceNotifier.getValue();

        balanceNotifier.getProperty().setValue(currentBalance + increment);
    }

    public void incTokens(int increment) {
        int currentTokens = tokenNotifier.getValue();

        tokenNotifier.getProperty().setValue(currentTokens + increment);
    }

    public void decTokens(int decrement) {
        int currentTokens = tokenNotifier.getValue();

        tokenNotifier.getProperty().setValue(currentTokens - decrement);
    }
}
