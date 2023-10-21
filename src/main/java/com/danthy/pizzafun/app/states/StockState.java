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
    private final ObservableValue<Double> rateSpeedNotifier;

    private final ObservableValue<Double> timerToNextRestockNotifier;

    private final ObservableList<ItemStockModel> itemStockModelNotifierList;

    private final ObservableValue<Integer> currentStockWeightNotifier;

    private final ObservableValue<Integer> totalStockWeightNotifier;

    public StockState(RoomModel roomModel) {
        itemStockModelNotifierList = FXCollections.observableArrayList(roomModel.getStockModel().getItemStockModels());
        timerToNextRestockNotifier = new ObservableValue<>(50.0);
        rateSpeedNotifier = new ObservableValue<>(1.0);

        int sumItemStockListWeight = itemStockModelNotifierList
                .stream()
                .map(itemStockModel -> itemStockModel.getItemModel().getWeight() * itemStockModel.getQuantity())
                .reduce(0, Integer::sum);

        currentStockWeightNotifier = new ObservableValue<>(sumItemStockListWeight);
        totalStockWeightNotifier = new ObservableValue<>(roomModel.getStockModel().getTotalWeight());
    }

    public boolean containsItemModel(ItemModel itemModel) {
        for (ItemStockModel itemStockModel : itemStockModelNotifierList)
            if (itemStockModel.getItemModel().equals(itemModel)) return true;

        return false;
    }

    public void addItemStockModel(ItemStockModel itemStockModel) {
        itemStockModelNotifierList.add(itemStockModel);
    }

    public void incrementRateSpeed(double rateSpeed) {
        double currentRateSpeed = rateSpeedNotifier.getValue();

        rateSpeedNotifier.getProperty().setValue(currentRateSpeed + rateSpeed);
    }

    public void incrementTotalStockWeight(int weight) {
        int currentTotalWeight = totalStockWeightNotifier.getValue();

        totalStockWeightNotifier.getProperty().setValue(currentTotalWeight + weight);
    }
}
