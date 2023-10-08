package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.RoomModel;
import lombok.Getter;

public class RoomWrapper implements IWrapperModel<RoomModel> {
    private final RoomModel roomModel;

    @Getter
    private final ObservableValue<Double> balanceObservable;

    @Getter
    private final ObservableValue<Integer> tokensObservable;

    public RoomWrapper(RoomModel roomModel) {
        this.roomModel = roomModel;

        balanceObservable = new ObservableValue<>(roomModel.getBalance());
        tokensObservable = new ObservableValue<>(roomModel.getTokens());
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


    @Override
    public RoomModel getWrapped() {
        return this.roomModel;
    }
}
