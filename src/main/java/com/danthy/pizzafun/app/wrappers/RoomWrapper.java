package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.RoomModel;
import lombok.Getter;

public class RoomWrapper implements IWrapperModel<RoomModel> {
    private final RoomModel roomModel;

    @Getter
    private final ObservableValue<Double> balanceObservableValue;

    @Getter
    private final ObservableValue<Integer> tokensObservableValue;

    public RoomWrapper(RoomModel roomModel) {
        this.roomModel = roomModel;

        balanceObservableValue = new ObservableValue<>(roomModel.getBalance());
        tokensObservableValue = new ObservableValue<>(roomModel.getTokens());
    }

    public void decBalance(double decrement) {
        double currentBalance = balanceObservableValue.getValue();

        balanceObservableValue.getProperty().setValue(currentBalance - decrement);
    }

    public void incBalance(double increment) {
        double currentBalance = balanceObservableValue.getValue();

        balanceObservableValue.getProperty().setValue(currentBalance + increment);
    }

    public void incTokens(int increment) {
        int currentTokens = tokensObservableValue.getValue();

        tokensObservableValue.getProperty().setValue(currentTokens + increment);
    }

    public void decTokens(int decrement) {
        int currentTokens = tokensObservableValue.getValue();

        tokensObservableValue.getProperty().setValue(currentTokens - decrement);
    }


    @Override
    public RoomModel getWrapped() {
        return this.roomModel;
    }
}
