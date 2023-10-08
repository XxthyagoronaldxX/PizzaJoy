package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapper;
import com.danthy.pizzafun.domain.models.ItemModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public interface IItemStockWrapper extends IWrapper<ItemStockModel> {
    String getName();

    ItemModel getItem();

    void decrementQuantity(int quantity);

    void incrementQuantity(int quantity);

    int getQuantity();

    String getQuantityPrint();
}
