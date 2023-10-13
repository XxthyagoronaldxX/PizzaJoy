package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.events.ProducedOrderEvent;
import com.danthy.pizzafun.app.events.ReStockEvent;
import com.danthy.pizzafun.app.events.SetSupplierEvent;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IStockService;
import com.danthy.pizzafun.app.states.StockState;
import com.danthy.pizzafun.app.utils.TimelineUtil;
import com.danthy.pizzafun.domain.models.*;
import javafx.beans.property.Property;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class StockServiceImpl implements IStockService {
    private StockState stockState;

    @Override
    public void onBoostRateSpeedEvent(MouseEvent event) {
        double currentRateSpeed = stockState.getRateSpeedObservable().getValue();

        getRateSpeedProperty().setValue(currentRateSpeed + 0.1);

        TimelineUtil.runFunctionAfterTimeInSeconds(2, (timelineEvent) -> {
            getRateSpeedProperty().setValue(getRateSpeedProperty().getValue() - 0.1);
        });
    }

    @Override
    public boolean isRemoveOrderValid(OrderModel orderModel) {
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

        for (ItemStockModel itemStockModel : stockState.getItemStockModelObservableList()) {
            ItemModel itemModel = itemStockModel.getItemModel();

            for (ItemPizzaModel itemPizzaModel : itemPizzaModelList) {
                ItemModel itemModelAux = itemPizzaModel.getItem();

                if (itemModel.equals(itemModelAux)) {
                    if (itemPizzaModel.getQuantity() > itemStockModel.getQuantity()) return false;
                }
            }
        }

        return true;
    }

    @Override
    public void removeItemStockFromOrder(OrderModel orderModel) {
        ObservableList<ItemStockModel> itemStockWrapperObservableList = stockState.getItemStockModelObservableList();
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

        for (int i = 0; i < itemStockWrapperObservableList.size(); i++) {
            ItemStockModel itemStockModel = itemStockWrapperObservableList.get(i);
            ItemModel itemModel = itemStockModel.getItemModel();

            for (ItemPizzaModel itemPizzaModel : itemPizzaModelList) {
                ItemModel itemModelAux = itemPizzaModel.getItem();

                if (itemModel.equals(itemModelAux)) {
                    itemStockModel.decrementQuantity(itemPizzaModel.getQuantity());
                    itemStockWrapperObservableList.set(i, itemStockModel);
                    break;
                }
            }
        }
    }

    @Override
    public ObservableList<ItemStockModel> getItemStockModelObservableList() {
        return stockState.getItemStockModelObservableList();
    }

    @Override
    public Property<Double> getTimerToNextRestockProperty() {
        return stockState.getTimerToNextRestockObservable().getProperty();
    }

    @Override
    public Property<Integer> getCurrentStockWeightProperty() {
        return stockState.getCurrentStockWeight().getProperty();
    }

    @Override
    public Property<Integer> getTotalStockWeightProperty() {
        return stockState.getTotalStockWeight().getProperty();
    }

    @Override
    public Property<Double> getRateSpeedProperty() {
        return stockState.getRateSpeedObservable().getProperty();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == ReStockEvent.class) onRestockEvent(event);
        else if (event.getClass() == StartGameEvent.class) onStartGameEvent(event);
        else if (event.getClass() == SetSupplierEvent.class) onSetSupplierEvent(event);
        else if (event.getClass() == ProducedOrderEvent.class) onProducedOrderEvent(event);
    }

    private void onRestockEvent(IEvent event) {
        ReStockEvent reStockEvent = (ReStockEvent) event;
        SupplierModel supplierModel = reStockEvent.supplierModel();

        getTimerToNextRestockProperty().setValue(supplierModel.getDeliveryTimeInSeconds());

        ObservableList<ItemStockModel> itemStockWrapperObservableList = stockState.getItemStockModelObservableList();

        int itemMaxWeight = ApplicationProperties.itemMaxWeight;
        int stockWeightGained = 0;
        for (int i = 0; i < itemStockWrapperObservableList.size(); i++) {
            ItemStockModel itemStockModel = itemStockWrapperObservableList.get(i);

            int weight = itemStockModel.getItemModel().getWeight();
            int quantity = itemMaxWeight - weight + 1;
            itemStockModel.incrementQuantity(quantity);
            stockWeightGained += quantity * weight;

            itemStockWrapperObservableList.set(i, itemStockModel);
        }

        int currentStockWeight = getCurrentStockWeightProperty().getValue();
        getCurrentStockWeightProperty().setValue(currentStockWeight + stockWeightGained);
    }

    private void onStartGameEvent(IEvent event) {
        stockState = GetIt.getInstance().find(StockState.class);
    }

    private void onSetSupplierEvent(IEvent event) {
        SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;
        SupplierModel supplierModel = setSupplierEvent.supplierModel();

        getTimerToNextRestockProperty().setValue(supplierModel.getDeliveryTimeInSeconds());
    }

    public void onProducedOrderEvent(IEvent event) {
        ProducedOrderEvent producedOrderEvent = (ProducedOrderEvent) event;

        int stockWeightLosted = producedOrderEvent.orderWrapper()
                .getOrderModel()
                .getPizzaModel()
                .getItemPizzaModels()
                .stream()
                .map(itemPizzaModel -> itemPizzaModel.getQuantity() * itemPizzaModel.getItem().getWeight())
                .reduce(0, Integer::sum);

        int currentStockWeight = getCurrentStockWeightProperty().getValue();
        getCurrentStockWeightProperty().setValue(currentStockWeight - stockWeightLosted);
    }
}
