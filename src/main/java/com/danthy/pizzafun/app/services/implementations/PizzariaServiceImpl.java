package com.danthy.pizzafun.app.services.implementations;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.contracts.IEvent;
import com.danthy.pizzafun.app.contracts.IListener;
import com.danthy.pizzafun.app.events.StartGameEvent;
import com.danthy.pizzafun.app.logic.GetIt;
import com.danthy.pizzafun.app.services.IPizzariaService;
import com.danthy.pizzafun.app.wrappers.ItemStockWrapper;
import com.danthy.pizzafun.app.wrappers.OrderWrapper;
import com.danthy.pizzafun.app.states.PizzariaState;
import com.danthy.pizzafun.domain.models.*;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

@Getter
public class PizzariaServiceImpl implements IPizzariaService, IListener {
    private PizzariaState pizzariaState;

    @Override
    public void addOrder(OrderWrapper orderWrapper) {
        this.pizzariaState.getOrderModelObservableList().add(orderWrapper);
    }

    @Override
    public void restockBySupplier(SupplierModel supplierModel) {
        ObservableList<ItemStockWrapper> itemStockWrapperObservableList = pizzariaState.getItemStockModelObservableList();
        int itemMaxWeight = ApplicationProperties.itemMaxWeight;

        for (int i = 0;i < itemStockWrapperObservableList.size();i++) {
            ItemStockWrapper itemStockWrapper = itemStockWrapperObservableList.get(i);

            itemStockWrapper.incrementQuantity(itemMaxWeight - itemStockWrapper.getItem().getWeight() + 1);

            itemStockWrapperObservableList.set(i, itemStockWrapper);
        }

        pizzariaState.getRoomWrapper().getWrapped().decBalance(supplierModel.getCost());
    }

    @Override
    public boolean isRemoveOrderValid(OrderModel orderModel) {
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

        for (ItemStockWrapper itemStockWrapper : pizzariaState.getItemStockModelObservableList()) {
            ItemModel itemModel = itemStockWrapper.getItem();

            for (ItemPizzaModel itemPizzaModel : itemPizzaModelList) {
                ItemModel itemModelAux = itemPizzaModel.getItem();

                if (itemModel.equals(itemModelAux)) {
                    if (itemPizzaModel.getQuantity() > itemStockWrapper.getQuantity()) return false;
                }
            }
        }

        return true;
    }

    @Override
    public void removeItemStockFromOrder(OrderModel orderModel) {
        ObservableList<ItemStockWrapper> itemStockWrapperObservableList = pizzariaState.getItemStockModelObservableList();
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

        for (int i = 0;i < itemStockWrapperObservableList.size();i++) {
            ItemStockWrapper itemStockWrapper = itemStockWrapperObservableList.get(i);
            ItemModel itemModel = itemStockWrapper.getItem();

            for (ItemPizzaModel itemPizzaModel : itemPizzaModelList) {
                ItemModel itemModelAux = itemPizzaModel.getItem();

                if (itemModel.equals(itemModelAux)) {
                    itemStockWrapper.decrementQuantity(itemPizzaModel.getQuantity());
                    itemStockWrapperObservableList.set(i, itemStockWrapper);
                    break;
                }
            }
        }
    }

    @Override
    public void removeOrder(OrderWrapper orderWrapper) {
        RoomModel roomModel = pizzariaState.getRoomWrapper().getWrapped();
        pizzariaState.getOrderModelObservableList().remove(orderWrapper);

        roomModel.incBalance(orderWrapper.getOrderModel().getPizzaModel().getPrice());
        roomModel.setTokens(roomModel.getTokens() + 1);
    }

    @Override
    public int getOrdersAmount() {
        return pizzariaState.getOrderModelObservableList().size();
    }

    @Override
    public void update(IEvent event) {
        if (event.getClass() == StartGameEvent.class) {
            this.pizzariaState = GetIt.getInstance().find(PizzariaState.class);
        }
    }
}
