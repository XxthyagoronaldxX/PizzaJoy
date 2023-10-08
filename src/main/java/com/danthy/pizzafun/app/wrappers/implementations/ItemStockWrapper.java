package com.danthy.pizzafun.app.wrappers.implementations;

import com.danthy.pizzafun.app.wrappers.IItemStockWrapper;
import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public class ItemStockWrapper implements IItemStockWrapper {
    private final ItemStockModel itemStockModel;

    public ItemStockWrapper(ItemStockModel itemStockModel) {
        this.itemStockModel = itemStockModel;
    }

    @Override
    public String getName() {
        return this.itemStockModel.getItemModel().getName();
    }

    @Override
    public ItemModel getItem() {
        return this.itemStockModel.getItemModel();
    }

    @Override
    public void decrementQuantity(int quantity) {
        this.itemStockModel.decrementQuantity(quantity);
    }

    @Override
    public void incrementQuantity(int quantity) {
        this.itemStockModel.incrementQuantity(quantity);
    }

    @Override
    public int getQuantity() {
        return this.itemStockModel.getQuantity();
    }

    @Override
    public String getQuantityPrint() {
        return this.itemStockModel.getQuantity() + "x";
    }

    @Override
    public ItemStockModel getWrappe() {
        return itemStockModel;
    }
}
