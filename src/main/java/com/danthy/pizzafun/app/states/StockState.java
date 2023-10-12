package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

@Getter
public class StockState {
    private final ObservableValue<Double> rateSpeedObservable;

    private final ObservableValue<Double> timerToNextRestockObservable;

    private final ObservableList<ItemStockModel> itemStockModelObservableList;

    public StockState(RoomModel roomModel) {
        this.itemStockModelObservableList = FXCollections.observableArrayList(roomModel.getStockModel().getItemStockModels());
        this.timerToNextRestockObservable = new ObservableValue<>(50.0);
        this.rateSpeedObservable = new ObservableValue<>(1.0);
    }
}
