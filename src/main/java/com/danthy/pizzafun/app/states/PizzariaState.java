package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class PizzariaState {
    private final RoomWrapper roomWrapper;

    private final ObservableValue<Double> balanceObservable;

    private final ObservableValue<Integer> tokensObservable;

    private final ObservableList<OrderWrapper> orderModelObservableList;

    public PizzariaState(RoomWrapper roomWrapper) {
        this.roomWrapper = roomWrapper;

        balanceObservable = new ObservableValue<>(roomWrapper.getWrapped().getBalance());
        tokensObservable = new ObservableValue<>(roomWrapper.getWrapped().getTokens());

        this.orderModelObservableList = FXCollections.observableArrayList();
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
