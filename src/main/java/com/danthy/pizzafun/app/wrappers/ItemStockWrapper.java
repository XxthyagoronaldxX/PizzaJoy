package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public class ItemStockWrapper implements IWrapperModel<ItemStockModel> {
    private final ItemStockModel itemStockModel;

    public ItemStockWrapper(ItemStockModel itemStockModel) {
        this.itemStockModel = itemStockModel;
    }

    public String getName() {
        return this.itemStockModel.getItemModel().getName();
    }

    public ItemModel getItem() {
        return this.itemStockModel.getItemModel();
    }

    public void decrementQuantity(int quantity) {
        this.itemStockModel.decrementQuantity(quantity);
    }

    public void incrementQuantity(int quantity) {
        this.itemStockModel.incrementQuantity(quantity);
    }

    public int getQuantity() {
        return this.itemStockModel.getQuantity();
    }

    public String getQuantityPrint() {
        return this.itemStockModel.getQuantity() + "x";
    }

    public ItemStockModel getWrappe() {
        return itemStockModel;
    }

    @Override
    public ItemStockModel getWrapped() {
        return this.itemStockModel;
    }
}
