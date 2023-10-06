package com.danthy.pizzafun.domain.models;

public class ItemStockModel {

    private ItemModel itemModel;
    private int quantity;

    public ItemStockModel(ItemModel itemModels, int quantity) {
        this.itemModel = itemModels;
        this.quantity = quantity;
    }

    public int getWeight() {
        return this.itemModel.getWeight() * quantity;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModels) {
        this.itemModel = itemModels;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void decrementQuantity(int quantity) {
        this.quantity -= quantity;
    }
}
