package com.danthy.pizzafun.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemStockModel {
    @EqualsAndHashCode.Include
    private UUID id;

    private ItemModel itemModel;

    private int quantity;

    public ItemStockModel(ItemModel itemModels, int quantity) {
        this.itemModel = itemModels;
        this.quantity = quantity;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrementQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
