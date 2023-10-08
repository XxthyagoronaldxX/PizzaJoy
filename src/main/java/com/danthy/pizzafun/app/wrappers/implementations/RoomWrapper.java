package com.danthy.pizzafun.app.wrappers.implementations;

import com.danthy.pizzafun.app.config.ApplicationProperties;
import com.danthy.pizzafun.app.wrappers.IRoomWrapper;
import com.danthy.pizzafun.domain.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

import java.util.List;

public class RoomWrapper implements IRoomWrapper {
    private final RoomModel roomModel;

    @Getter
    private final ObservableList<OrderWrapper> orderModelObservableList;

    @Getter
    private final ObservableList<ItemStockWrapper> itemStockModelObservableList;

    @Getter
    private SupplierWrapper nextSupplierWrapper;

    private SupplierWrapper supplierWrapper;

    public RoomWrapper(RoomModel roomModel, SupplierModel supplierModel) {
        this.roomModel = roomModel;

        this.supplierWrapper = new SupplierWrapper(supplierModel);
        this.orderModelObservableList = FXCollections.observableArrayList();

        List<ItemStockWrapper> itemStockWrapperList = roomModel
                .getStockModel()
                .getItemStockModels()
                .stream()
                .map(ItemStockWrapper::new)
                .toList();

        this.itemStockModelObservableList = FXCollections.observableArrayList(itemStockWrapperList);
    }

    @Override
    public void addOrder(OrderWrapper orderWrapper) {
        this.orderModelObservableList.add(orderWrapper);
    }

    @Override
    public void restockBySupplier(SupplierModel supplierModel) {
        int itemMaxWeight = ApplicationProperties.itemMaxWeight;

        for (int i = 0;i < itemStockModelObservableList.size();i++) {
            ItemStockWrapper itemStockWrapper = itemStockModelObservableList.get(i);

            itemStockWrapper.incrementQuantity(itemMaxWeight - itemStockWrapper.getItem().getWeight() + 1);

            itemStockModelObservableList.set(i, itemStockWrapper);
        }

        roomModel.decBalance(supplierModel.getCost());
    }

    @Override
    public boolean isRemoveOrderValid(OrderModel orderModel) {
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

        for (ItemStockWrapper itemStockWrapper : itemStockModelObservableList) {
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
        List<ItemPizzaModel> itemPizzaModelList = orderModel.getPizzaModel().getItemPizzaModels();

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

    @Override
    public void removeOrder(OrderWrapper orderWrapper) {
        this.orderModelObservableList.remove(orderWrapper);

        roomModel.incBalance(orderWrapper.getOrderModel().getPizzaModel().getPrice());
        roomModel.setTokens(roomModel.getTokens() + 1);
    }

    @Override
    public SupplierWrapper getSupplierWrapper() {
        if (nextSupplierWrapper != null) {
            supplierWrapper = nextSupplierWrapper;
            nextSupplierWrapper = null;
        }

        return supplierWrapper;
    }

    @Override
    public void setNextSupplierWrapper(SupplierWrapper supplierWrapper) {
        if (roomModel.getTokens() > supplierWrapper.getBuyToken()) {
            nextSupplierWrapper = supplierWrapper;

            roomModel.setTokens(roomModel.getTokens() - supplierWrapper.getBuyToken());
        }
    }

    @Override
    public String getBalancePrint() {
        return String.format("Dinheiro: $%.2f", roomModel.getBalance());
    }

    @Override
    public double getBalance() {
        return roomModel.getBalance();
    }

    @Override
    public String getTokensPrint() {return String.format("Tokens: %d", roomModel.getTokens());}

    @Override
    public int getOrdersAmount() {
        return this.orderModelObservableList.size();
    }

    @Override
    public RoomModel getWrappe() {
        return this.roomModel;
    }
}
