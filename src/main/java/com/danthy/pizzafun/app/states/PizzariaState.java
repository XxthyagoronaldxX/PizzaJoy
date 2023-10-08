package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class PizzariaState {
    private final RoomWrapper roomWrapper;

    private final ObservableList<OrderWrapper> orderModelObservableList;

    public PizzariaState(RoomWrapper roomWrapper) {
        this.roomWrapper = roomWrapper;

        this.orderModelObservableList = FXCollections.observableArrayList();
    }
}
