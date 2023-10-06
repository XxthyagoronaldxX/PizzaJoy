package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemPizzaModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;
import com.danthy.pizzafun.domain.models.RoomModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class RoomWrapper {
    private final RoomModel roomModel;

    private final ObservableList<OrderItemListWrapper> orderModelObservableList;

    private final ObservableList<ItemStockWrapper> itemStockModelObservableList;

    public RoomWrapper(RoomModel roomModel) {
        this.roomModel = roomModel;

        this.orderModelObservableList = FXCollections.observableArrayList();

        List<ItemStockWrapper> itemStockWrapperList = roomModel
                .getStockModel()
                .getItemStockModels()
                .stream()
                .map(ItemStockWrapper::new)
                .toList();

        this.itemStockModelObservableList = FXCollections.observableArrayList(itemStockWrapperList);
    }

    public void addOrder(OrderItemListWrapper orderItemListWrapper) {
        this.orderModelObservableList.add(orderItemListWrapper);
    }

    public void removeOrder(OrderItemListWrapper orderItemListWrapper) {
        this.orderModelObservableList.remove(orderItemListWrapper);

        roomModel.incBalance(orderItemListWrapper.getOrderModel().getPizzaModel().getPrice());

        refreshItemStockListFromOrderProduced(orderItemListWrapper);
    }

    private void refreshItemStockListFromOrderProduced(OrderItemListWrapper orderItemListWrapper) {
        List<ItemPizzaModel> itemPizzaModelList = orderItemListWrapper.getOrderModel().getPizzaModel().getItemPizzaModels();

        for (int i = 0;i < itemStockModelObservableList.size();i++) {
            ItemStockWrapper itemStockWrapper = itemStockModelObservableList.get(i);
            ItemModel itemModel = itemStockWrapper.getItem();

            for (ItemPizzaModel itemPizzaModel : itemPizzaModelList) {
                ItemModel itemModelAux = itemPizzaModel.getItem();

                if (itemModel.equals(itemModelAux)) {
                    itemStockWrapper.decrementQuantity(itemPizzaModel.getQuantity());
                    itemStockModelObservableList.set(i, itemStockWrapper);
                    break;
                }
            }
        }
    }

    public String getBalancePrint() {
        return String.format("Dinheiro: $%.2f", roomModel.getBalance());
    }

    public int getOrdersAmount() {
        return this.orderModelObservableList.size();
    }

    public ObservableList<OrderItemListWrapper> getOrderModelObservableList() {
        return orderModelObservableList;
    }

    public ObservableList<ItemStockWrapper> getItemStockModelObservableList() {
        return itemStockModelObservableList;
    }
}
