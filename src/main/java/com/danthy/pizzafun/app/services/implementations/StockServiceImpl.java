package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
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
import lombok.Getter;

import java.util.List;

@Getter
public class StockServiceImpl implements IStockService, IListener {
    private StockState stockState;

    public StockServiceImpl() {
    }

    @Override
    public Property<Double> getTimerToNextRestockProperty() {
        return stockState.getTimerToNextRestockObservable().getProperty();
    }

    @Override
    public Property<Double> getRateSpeedProperty() {
        return stockState.getRateSpeedObservable().getProperty();
    }

    @Override
    public void boostRateSpeed() {
        double currentRateSpeed = stockState.getRateSpeedObservable().getValue();

        getRateSpeedProperty().setValue(currentRateSpeed + 0.1);

        TimelineUtil.runFunctionAfterTimeInSeconds(2, (event) -> {
            getRateSpeedProperty().setValue(getRateSpeedProperty().getValue() - 0.1);
        });
    }

    @Override
    public void restockBySupplier(SupplierModel supplierModel) {
        getTimerToNextRestockProperty().setValue(supplierModel.getDeliveryTimeInSeconds());

        ObservableList<ItemStockModel> itemStockWrapperObservableList = stockState.getItemStockModelObservableList();

        int itemMaxWeight = ApplicationProperties.itemMaxWeight;

        for (int i = 0; i < itemStockWrapperObservableList.size(); i++) {
            ItemStockModel itemStockModel = itemStockWrapperObservableList.get(i);

            itemStockModel.incrementQuantity(itemMaxWeight - itemStockModel.getItemModel().getWeight() + 1);

            itemStockWrapperObservableList.set(i, itemStockModel);
        }
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
    public void update(IEvent event) {
        if (event.getClass() == ReStockEvent.class) {
            ReStockEvent reStockEvent = (ReStockEvent) event;
            SupplierModel supplierModel = reStockEvent.supplierModel();

            restockBySupplier(supplierModel);
        } else if (event.getClass() == StartGameEvent.class) {
            stockState = GetIt.getInstance().find(StockState.class);
        } else if (event.getClass() == SetSupplierEvent.class) {
            SetSupplierEvent setSupplierEvent = (SetSupplierEvent) event;
            SupplierModel supplierModel = setSupplierEvent.supplierModel();

            getTimerToNextRestockProperty().setValue(supplierModel.getDeliveryTimeInSeconds());
        }
    }
}
