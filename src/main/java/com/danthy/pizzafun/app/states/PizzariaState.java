package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class PizzariaState {
    private final RoomWrapper roomWrapper;

    private final ObservableList<OrderWrapper> orderModelObservableList;

    private final ObservableList<ItemStockWrapper> itemStockModelObservableList;

    public PizzariaState(RoomWrapper roomWrapper) {
        this.roomWrapper = roomWrapper;

        List<ItemStockWrapper> itemStockWrapperList = roomWrapper
                .getWrapped()
                .getStockModel()
                .getItemStockModels()
                .stream()
                .map(ItemStockWrapper::new)
                .toList();

        this.orderModelObservableList = FXCollections.observableArrayList();
        this.itemStockModelObservableList = FXCollections.observableArrayList(itemStockWrapperList);
    }
}
