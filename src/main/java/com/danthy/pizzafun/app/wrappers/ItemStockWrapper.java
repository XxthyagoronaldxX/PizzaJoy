package com.danthy.pizzafun.app.wrappers;

import com.danthy.pizzafun.app.contracts.IWrapperModel;
import com.danthy.pizzafun.domain.models.ItemStockModel;

public class ItemStockWrapper implements IWrapperModel<ItemStockModel> {
    private final ItemStockModel itemStockModel;

    public ItemStockWrapper(ItemStockModel itemStockModel) {
        this.itemStockModel = itemStockModel;
    }

    @Override
    public ItemStockModel getWrapped() {
        return this.itemStockModel;
    }
}
