package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import com.danthy.pizzafun.app.wrappers.RoomWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class StockState {
    private final ObservableList<ItemStockWrapper> itemStockModelObservableList;

    public StockState(RoomWrapper roomWrapper) {
        List<ItemStockWrapper> itemStockWrapperList = roomWrapper
                .getWrapped()
                .getStockModel()
                .getItemStockModels()
                .stream()
                .map(ItemStockWrapper::new)
                .toList();

        this.itemStockModelObservableList = FXCollections.observableArrayList(itemStockWrapperList);
    }
}
