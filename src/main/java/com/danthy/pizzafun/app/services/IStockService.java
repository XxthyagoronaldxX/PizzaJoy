package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.contracts.IService;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

public interface IStockService extends IService, IListener {
    void onBoostRateSpeedEvent(MouseEvent event);

    boolean isRemoveOrderValid(OrderModel orderModel);

    void removeItemStockFromOrder(OrderModel orderModel);

    ObservableList<ItemStockModel> getItemStockModelObservableList();

    Property<Double> getTimerToNextRestockProperty();

    Property<Integer> getCurrentStockWeightProperty();

    Property<Integer> getTotalStockWeightProperty();

    Property<Double> getRateSpeedProperty();
}
