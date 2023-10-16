package com.danthy.pizzafun.app.services;

import com.danthy.pizzafun.app.contracts.Service;
import com.danthy.pizzafun.app.logic.EventPublisher;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import com.danthy.pizzafun.domain.models.OrderModel;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

public abstract class StockService extends Service {
    protected StockService(EventPublisher eventPublisher) {
        super(eventPublisher);
    }

    public abstract void onBoostRateSpeedEvent(MouseEvent event);

    public abstract boolean isRemoveOrderValid(OrderModel orderModel);

    public abstract void removeItemStockFromOrder(OrderModel orderModel);

    public abstract ObservableList<ItemStockModel> getItemStockModelObservableList();

    public abstract Property<Double> getTimerToNextRestockProperty();

    public abstract Property<Integer> getCurrentStockWeightProperty();

    public abstract Property<Integer> getTotalStockWeightProperty();

    public abstract Property<Double> getRateSpeedProperty();
}
