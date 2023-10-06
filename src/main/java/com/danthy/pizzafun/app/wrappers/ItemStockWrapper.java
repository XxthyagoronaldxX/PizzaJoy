package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public class ItemStockWrapper {
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

    public String getQuantity() {
        return this.itemStockModel.getQuantity() + "x";
    }
}
