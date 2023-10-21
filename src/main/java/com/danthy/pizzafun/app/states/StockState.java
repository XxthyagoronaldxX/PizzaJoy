package com.danthy.pizzafun.app.states;

import com.danthy.pizzafun.app.logic.ObservableValue;
import com.danthy.pizzafun.domain.models.ItemModel;
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

    private final ObservableValue<Integer> currentStockWeight;

    private final ObservableValue<Integer> totalStockWeight;

    public StockState(RoomModel roomModel) {
        itemStockModelObservableList = FXCollections.observableArrayList(roomModel.getStockModel().getItemStockModels());
        timerToNextRestockObservable = new ObservableValue<>(50.0);
        rateSpeedObservable = new ObservableValue<>(1.0);

        int sumItemStockListWeight = itemStockModelObservableList
                .stream()
                .map(itemStockModel -> itemStockModel.getItemModel().getWeight() * itemStockModel.getQuantity())
                .reduce(0, Integer::sum);

        currentStockWeight = new ObservableValue<>(sumItemStockListWeight);
        totalStockWeight = new ObservableValue<>(roomModel.getStockModel().getTotalWeight());
    }

    public boolean containsItemModel(ItemModel itemModel) {
        for (ItemStockModel itemStockModel : itemStockModelObservableList)
            if (itemStockModel.getItemModel().equals(itemModel)) return true;

        return false;
    }

    public void addItemStockModel(ItemStockModel itemStockModel) {
        itemStockModelObservableList.add(itemStockModel);
    }

    public void incrementRateSpeed(double rateSpeed) {
        double currentRateSpeed = rateSpeedObservable.getValue();

        rateSpeedObservable.getProperty().setValue(currentRateSpeed + rateSpeed);
    }

    public void incrementTotalStockWeight(int weight) {
        int currentTotalWeight = totalStockWeight.getValue();

        totalStockWeight.getProperty().setValue(currentTotalWeight + weight);
    }
}
